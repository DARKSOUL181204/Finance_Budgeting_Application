package com.Finance.demo.Services.LedgerEntry;

import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.DTO.LedgerEntryDto;
import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.LedgerEntry;
import com.Finance.demo.Model.Transaction;
import com.Finance.demo.Model.Wallet;

import java.util.*;
import java.math.BigDecimal;


public interface ILedgerEntryServices {

    LedgerEntryDto getLedgerById(Long Id);
    BigDecimal getTotalAmount(Long Id);
    WalletDto getWalletByLedgerId(Long Id);
    TransactionDto getTransactionByLedgerId(Long Id);
    CategoryDto getCategoryByLedgerId(Long Id);
    List<LedgerEntryDto> getEntriesByWallet(Long walletId);

}
