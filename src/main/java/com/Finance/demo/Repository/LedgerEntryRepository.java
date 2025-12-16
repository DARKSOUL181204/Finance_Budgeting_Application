package com.Finance.demo.Repository;


import com.Finance.demo.Model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {
    List<LedgerEntry> findAllByWalletId(Long walletId);
    boolean existsByCategoryId(Long categoryId);
}
