package com.Finance.demo.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long Id;
    private String name;
    private BigDecimal balance;
    private String accountNumber;
}
