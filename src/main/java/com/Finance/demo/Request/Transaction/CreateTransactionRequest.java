package com.Finance.demo.Request.Transaction;

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
    private User user;

    private Long walletId;
    private Long categoryId;


}
