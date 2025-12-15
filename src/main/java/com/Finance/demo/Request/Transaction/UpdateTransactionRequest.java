package com.Finance.demo.Request.Transaction;

import com.Finance.demo.Model.LedgerEntry;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateTransactionRequest {

    private String description;
    private BigDecimal totalAmount;
    private Long walletId;
    private Long categoryId;

}
