package com.Finance.demo.Services.LedgerEntry;

import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.DTO.LedgerEntryDto;
import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.LedgerEntry;
import com.Finance.demo.Model.Transaction;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Repository.LedgerEntryRepository;
import com.Finance.demo.Repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LedgerEntryService implements ILedgerEntryServices{
    @Autowired
    private final LedgerEntryRepository ledgerRepository;
    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final ModelMapper modelMapper;


    @Override
    public LedgerEntryDto getLedgerById(Long Id) {
        LedgerEntry ledgerEntry =  ledgerRepository
                .findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Leger Entry not found") );

        return modelMapper.map(ledgerEntry, LedgerEntryDto.class);
    }

    @Override
    public BigDecimal getTotalAmount(Long Id) {
        return ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get LegerEntry Amount"))
                .getAmount();
    }

    @Override
    public WalletDto getWalletByLedgerId(Long Id) {
        Wallet wallet =  ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Wallet"))
                .getWallet();
    return modelMapper.map(wallet, WalletDto.class);
    }

    @Override
    public TransactionDto getTransactionByLedgerId(Long Id) {
        Transaction transaction =  ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Transaction"))
                .getTransaction();
        return modelMapper.map(transaction,TransactionDto.class);
    }

    @Override
    public CategoryDto getCategoryByLedgerId(Long Id) {
        Category category  = ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Category "))
                .getCategory();
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<LedgerEntryDto> getEntriesByWallet(Long walletId) {
        // check if wallet id exist or not
        if (!walletRepository.existsById(walletId)) {
            throw new ResourceNotFoundException("Wallet not found with Id: " + walletId);
        }
        // if empty return empty string
        List<LedgerEntry> entries =  ledgerRepository
                .findAllByWalletId(walletId);

        return entries.stream()
                .map(entry->modelMapper.map(entry, LedgerEntryDto.class))
                .collect(Collectors.toList());
          }
}
