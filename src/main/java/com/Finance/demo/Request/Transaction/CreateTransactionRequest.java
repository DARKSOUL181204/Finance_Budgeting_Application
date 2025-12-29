package com.Finance.demo.Request.Transaction;

import com.Finance.demo.Enums.TransactionType;
import com.Finance.demo.Model.LedgerEntry;
import com.Finance.demo.Model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@Data
@RequiredArgsConstructor
public class CreateTransactionRequest {

    private String description;
    private BigDecimal totalAmount;
    private TransactionType type;

    private Long walletId;
    private Long categoryId;


}
