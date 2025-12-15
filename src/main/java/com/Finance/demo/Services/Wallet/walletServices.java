package com.Finance.demo.Services.Wallet;

import com.Finance.demo.DTO.UserDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Exceptions.AlreadyExistException;
import com.Finance.demo.Exceptions.ResourceDoesNotExistException;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.User;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Repository.WalletRepository;
import com.Finance.demo.Request.Wallet.CreateWalletRequest;
import com.Finance.demo.Request.Wallet.UpdateWalletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class walletServices implements IWalletServices{

    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public WalletDto findWalletById(Long Id) {
        Wallet wallet =  walletRepository.findById(Id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Wallet not found Id : " + Id));
        return modelMapper.map(wallet,WalletDto.class);

    }

    @Override
    public WalletDto getWalletByAccountNumber(String  accountNumber) {
        Wallet wallet  = walletRepository.findByAccountNumber(accountNumber)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Account Number not Found : " + accountNumber));
        return modelMapper.map(wallet,WalletDto.class);
    }

    @Override
    public BigDecimal getBalanceById(Long Id) {
        return walletRepository.findById(Id)
                .map(Wallet::getBalance)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Wallet not found with id: " + Id));

    }

    @Override
    public UserDto getUserByWalletId(Long Id) {
        User user =  walletRepository.findById(Id)
                .map(Wallet :: getUser)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not Found By id : " + Id));
        return modelMapper.map(user,UserDto.class);
    }

    // have to cooperate user if userid does not exist then not possible
    @Override
    public WalletDto createWallet(CreateWalletRequest request) {
        Wallet walletToDto =  Optional.of(request)
                .filter(walletRequest -> !walletRepository.existsByAccountNumber(request.getAccountNumber()))
                .map(req ->{
                    User user = userRepository.findById(req.getUserId())
                            .orElseThrow(()-> new ResourceNotFoundException("User Id Invalid"));

                    Wallet wallet = new Wallet();
                    wallet.setName(req.getName());
                    wallet.setAccountNumber(req.getAccountNumber());
                    wallet.setBalance(req.getBalance());
                    wallet.setUser(user);
                    return walletRepository.save(wallet);
                })
                .orElseThrow(() -> new AlreadyExistException("Wallet already Exists !!"));
        return modelMapper.map(walletToDto,WalletDto.class);

    }

    @Override
    public void deleteWalletById(Long Id) {
        if (!walletRepository.existsById(Id)) {
            throw new ResourceDoesNotExistException("Wallet id does not exist : " + Id);
        }
        walletRepository.deleteById(Id);
    }

    @Override
    public WalletDto updateWallet(UpdateWalletRequest request,Long Id) {
        Wallet wallet = walletRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Wallet with Id :" + Id + " not found "));
        if(request.getName() != null && !request.getName().isEmpty()){
            wallet.setName(request.getName());
        }
        if(request.getAccountNumber() != null && !request.getAccountNumber().isEmpty()){
            // if account number already exists
            if (!request.getAccountNumber().equals(wallet.getAccountNumber())) {
            if(walletRepository.existsByAccountNumber(request.getAccountNumber())){
                throw new AlreadyExistException("account number already exist ");
            }
            wallet.setAccountNumber(request.getAccountNumber());
            }
        }
        walletRepository.save(wallet);
        return modelMapper.map(wallet,WalletDto.class);

    }
}
