package com.Finance.demo.Services.LedgerEntry;

import com.Finance.demo.Exceptions.ResourceNotFoundException;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.LedgerEntry;
import com.Finance.demo.Model.Transaction;
import com.Finance.demo.Model.Wallet;
import com.Finance.demo.Repository.LedgerEntryRepository;
import com.Finance.demo.Repository.WalletRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ledgerEntryServices implements ILedgerEntryServices{
    @Autowired
    private final LedgerEntryRepository ledgerRepository;
    @Autowired
    private final WalletRepository walletRepository;


    @Override
    public LedgerEntry getLedgerById(Long Id) {
        return ledgerRepository
                .findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Leger Entry not found") );
    }

    @Override
    public BigDecimal getTotalAmount(Long Id) {
        return ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get LegerEntry Amount"))
                .getAmount();
    }

    @Override
    public Wallet getWalletByLedgerId(Long Id) {
        return ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Wallet"))
                .getWallet();
    }

    @Override
    public Transaction getTransactionByLedgerId(Long Id) {
        return ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Transaction"))
                .getTransaction();
    }

    @Override
    public Category getCategoryByLedgerId(Long Id) {
        return ledgerRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Ledger Entry not found , unable to get Category "))
                .getCategory();
    }

    @Override
    public List<LedgerEntry> getEntriesByWallet(Long walletId) {
        // check if wallet id exist or not
        if (!walletRepository.existsById(walletId)) {
            throw new ResourceNotFoundException("Wallet not found with Id: " + walletId);
        }
        // if empty return empty string
        return ledgerRepository
                .findAllByWalletId(walletId);
          }
}
