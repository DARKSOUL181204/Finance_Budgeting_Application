package com.Finance.demo.Services.LedgerEntry;

import com.Finance.demo.Model.Category;
import com.Finance.demo.Model.LedgerEntry;
import com.Finance.demo.Model.Transaction;
import com.Finance.demo.Model.Wallet;

import java.util.*;
import java.math.BigDecimal;




public interface ILedgerEntryServices {

    LedgerEntry getLedgerById(Long Id);
    BigDecimal getTotalAmount(Long Id);
    Wallet getWalletByLedgerId(Long Id);
    Transaction getTransactionByLedgerId(Long Id);
    Category getCategoryByLedgerId(Long Id);
    List<LedgerEntry> getEntriesByWallet(Long walletId);

}
