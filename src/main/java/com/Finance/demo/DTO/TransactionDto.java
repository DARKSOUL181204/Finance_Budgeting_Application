package com.Finance.demo.DTO;

import com.Finance.demo.Model.LedgerEntry;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class TransactionDto {
    private Long Id;
    private LocalDateTime date;
    private String description;
    private BigDecimal totalAmount;
}
