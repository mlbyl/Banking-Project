package com.mlbyl.bankingproject.repository;

import com.mlbyl.bankingproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(UUID userId);

    Optional<Account> findByIBAN(String iban);
}
