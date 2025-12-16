package com.Finance.demo.Controller;


import com.Finance.demo.DTO.CategoryDto;
import com.Finance.demo.DTO.LedgerEntryDto;
import com.Finance.demo.DTO.TransactionDto;
import com.Finance.demo.DTO.WalletDto;
import com.Finance.demo.Response.ApiResponse;
import com.Finance.demo.Services.LedgerEntry.ILedgerEntryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/ledgerEntry")
public class LedgerEntryController {

    private final ILedgerEntryServices ledgerEntryServices;

    @GetMapping("/ledger/{Id}")
    ResponseEntity<ApiResponse> getLedgerById(@PathVariable Long Id){
        LedgerEntryDto ledgerEntry = ledgerEntryServices.getLedgerById(Id);
        return ResponseEntity.ok(new ApiResponse("Fetch Ledger Successfully",ledgerEntry));
    }
    @GetMapping("/amount/{Id}")
    ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long Id){
        BigDecimal totalAmount = ledgerEntryServices.getTotalAmount(Id);
        return ResponseEntity.ok(new ApiResponse("Fetch totalAmount Successfully",totalAmount));
    }
    @GetMapping("/get/wallet/{Id}")
    ResponseEntity<ApiResponse> getWalletByLedgerId(@PathVariable Long Id){
        WalletDto wallet = ledgerEntryServices.getWalletByLedgerId(Id);
        return ResponseEntity.ok(new ApiResponse("Fetch Wallet Successfully",wallet));
    }
    @GetMapping("/transaction/{Id}")
    ResponseEntity<ApiResponse> getTransactionByLedgerId(@PathVariable Long Id){
        TransactionDto transaction = ledgerEntryServices.getTransactionByLedgerId(Id);
        return ResponseEntity.ok(new ApiResponse("Fetch Transaction Successfully",transaction));
    }
    @GetMapping("/category/{Id}")
    ResponseEntity<ApiResponse> getCategoryByLedgerId(@PathVariable Long Id){
        CategoryDto category = ledgerEntryServices.getCategoryByLedgerId(Id);
        return ResponseEntity.ok(new ApiResponse("Fetch category Successfully",category));

    }
    @GetMapping("entries/{walletId}")
    ResponseEntity<ApiResponse> getEntriesByWallet(@PathVariable Long walletId){
        List<LedgerEntryDto> ledgerEntries = ledgerEntryServices.getEntriesByWallet(walletId);
        return ResponseEntity.ok(new ApiResponse("Fetch Ledger Successfully",ledgerEntries));
    }
}
