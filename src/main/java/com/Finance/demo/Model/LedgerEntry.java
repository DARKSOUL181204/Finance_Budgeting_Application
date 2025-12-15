package com.Finance.demo.Model;

import com.Finance.demo.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet ;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

}
