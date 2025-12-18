
package com.example.atm.repository;

import com.example.atm.model.Account;
import com.example.atm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndCreatedAtBetweenOrderByCreatedAtDesc(Account account, Instant from, Instant to);
    List<Transaction> findTop50ByAccountOrderByCreatedAtDesc(Account account);
}
