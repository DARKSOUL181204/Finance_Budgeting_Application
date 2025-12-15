package com.Finance.demo.Request.Wallet;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateWalletRequest {
    private String name;
    private BigDecimal balance;
    private String accountNumber;
    private Long userId;
}
