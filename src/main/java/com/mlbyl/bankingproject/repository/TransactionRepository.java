package com.mlbyl.bankingproject.repository;

import com.mlbyl.bankingproject.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(UUID userId);

    List<Transaction> findBytoAccountId(Long toAccountId);

    List<Transaction> findByfromAccountId(Long fromAccountId);
}
