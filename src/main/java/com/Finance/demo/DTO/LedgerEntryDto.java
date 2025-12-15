package com.Finance.demo.DTO;

import com.Finance.demo.Enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LedgerEntryDto {
    private Long Id;
    private BigDecimal amount;
    private TransactionType type;
    private String walletName;
    private Long walletId;
    private String categoryName;
    private Long categoryId;
}
