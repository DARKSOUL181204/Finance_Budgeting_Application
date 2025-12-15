package com.Finance.demo.Services.Transaction;

import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.Enums.TransactionType;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.*;
import com.Finance.demo.Repository.CategoryRepository;
import com.Finance.demo.Repository.TransactionRepository;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Repository.WalletRepository;
import com.Finance.demo.Request.Transaction.CreateTransactionRequest;
import com.Finance.demo.Request.Transaction.UpdateTransactionRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class transactionServices implements ITransactionServices {

    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found !!"));

        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not Found !!"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not Found !!"));

        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setDescription(request.getDescription());
        transaction.setTotalAmount(request.getTotalAmount());
        transaction.setUser(user);

        LedgerEntry walletEntry = new LedgerEntry();
        walletEntry.setAmount(request.getTotalAmount());
        walletEntry.setType(TransactionType.CREDIT);
        walletEntry.setWallet(wallet);
        walletEntry.setCategory(null);
        walletEntry.setTransaction(transaction);

        LedgerEntry categoryEntry = new LedgerEntry();
        categoryEntry.setAmount(request.getTotalAmount());
        categoryEntry.setType(TransactionType.DEBIT);
        categoryEntry.setWallet(null);
        categoryEntry.setCategory(category);
        categoryEntry.setTransaction(transaction);

        transaction.setEntries(Arrays.asList(walletEntry, categoryEntry));

        BigDecimal newBalance = wallet.getBalance().subtract(request.getTotalAmount());
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionDto getTransactionById(Long Id) {
        Transaction transaction =  transactionRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction Id not found !!"));
        return modelMapper.map(transaction,TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getAllTransactionsByUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found ");
        }
        return transactionRepository.findByUserId(userId).stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDto updateTransaction(UpdateTransactionRequest request, Long Id) {
        Transaction transaction = transactionRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction Id Invalid !!"));

        if(request.getDescription() != null && !request.getDescription().isEmpty()){
            transaction.setDescription(request.getDescription());
        }
        if(request.getWalletId() != null ){
           LedgerEntry walletEntry = transaction.getEntries()
                            .stream()
                            .filter(entry-> entry.getWallet() != null)
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Corrupted Data: No Wallet Entry found"));
           Wallet oldWallet = walletEntry.getWallet();
            if (!oldWallet.getId().equals(request.getWalletId())){
                Wallet newWallet = walletRepository.findById(request.getWalletId())
                        .orElseThrow(() -> new ResourceNotFoundException("New Wallet not found"));
                // update old
                oldWallet.setBalance(oldWallet.getBalance().add(walletEntry.getAmount()));
                walletRepository.save(oldWallet);
                // update new
                newWallet.setBalance(newWallet.getBalance().subtract(walletEntry.getAmount()));
                walletRepository.save(newWallet);
                walletEntry.setWallet(newWallet);
            }


        } if (request.getTotalAmount() != null) {
            BigDecimal oldAmount = transaction.getTotalAmount();
            BigDecimal newAmount = request.getTotalAmount();

            if (oldAmount.compareTo(newAmount) != 0) {

                LedgerEntry walletEntry = transaction.getEntries().stream()
                        .filter(entry -> entry.getWallet() != null)
                        .findFirst().orElseThrow();

                Wallet currentWallet = walletEntry.getWallet();

                BigDecimal balanceAfterRefund = currentWallet.getBalance().add(oldAmount);
                BigDecimal finalBalance = balanceAfterRefund.subtract(newAmount);

                currentWallet.setBalance(finalBalance);
                walletRepository.save(currentWallet);

                transaction.setTotalAmount(newAmount);

                transaction.getEntries().forEach(entry -> entry.setAmount(newAmount));
            }
        }

        if (request.getCategoryId() != null) {
            LedgerEntry categoryEntry = transaction.getEntries().stream()
                    .filter(entry -> entry.getCategory() != null)
                    .findFirst()
                    .orElseThrow();

            if(!categoryEntry.getCategory().getId().equals(request.getCategoryId())){
                Category newCategory = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
                categoryEntry.setCategory(newCategory);
            }
        }

        // 6. Save & Return DTO
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDto.class);


    }

    @Override
    public UserDto getUserByTransactionId(Long Id){
        User user =  transactionRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction Id Invalid !!"))
                .getUser();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteTransaction(Long Id) {
        Transaction transaction = transactionRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        LedgerEntry walletEntry = transaction.getEntries().stream()
                .filter(e -> e.getWallet() != null)
                .findFirst().orElse(null);
        if (walletEntry != null) {
            Wallet wallet = walletEntry.getWallet();
            wallet.setBalance(wallet.getBalance().add(walletEntry.getAmount()));
            walletRepository.save(wallet);
        }
        transactionRepository.deleteById(Id);
    }
}
