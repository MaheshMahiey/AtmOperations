
package com.example.atm.service;

import com.example.atm.exception.BadRequestException;
import com.example.atm.exception.NotFoundException;
import com.example.atm.model.Account;
import com.example.atm.model.Transaction;
import com.example.atm.model.TxnType;
import com.example.atm.repository.AccountRepository;
import com.example.atm.repository.TransactionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Account getAndVerify(String accountNumber, String pin) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found: " + accountNumber));
        if (!passwordEncoder.matches(pin, account.getPinHash())) {
            throw new BadRequestException("Invalid PIN");
        }
        return account;
    }

    @Transactional
    public Account deposit(String accountNumber, String pin, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Deposit amount must be positive");
        }
        Account account = getAndVerify(accountNumber, pin);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        transactionRepository.save(new Transaction(account, TxnType.DEPOSIT, amount, account.getBalance()));
        return account;
    }

    @Transactional
    public Account withdraw(String accountNumber, String pin, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Withdrawal amount must be positive");
        }
        Account account = getAndVerify(accountNumber, pin);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        transactionRepository.save(new Transaction(account, TxnType.WITHDRAWAL, amount, account.getBalance()));
        return account;
    }

    @Transactional
    public void changePin(String accountNumber, String oldPin, String newPin) {
        if (newPin == null || newPin.length() < 4 || newPin.length() > 12) {
            throw new BadRequestException("New PIN length must be between 4 and 12");
        }
        Account account = getAndVerify(accountNumber, oldPin);
        account.setPinHash(passwordEncoder.encode(newPin));
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getStatement(String accountNumber, String pin, Instant from, Instant to) {
        Account account = getAndVerify(accountNumber, pin);
        if (from == null || to == null) {
            // default: last 30 days
            to = Instant.now();
            from = to.minusSeconds(30L * 24 * 3600);
        }
        return transactionRepository.findByAccountAndCreatedAtBetweenOrderByCreatedAtDesc(account, from, to);
    }
}
