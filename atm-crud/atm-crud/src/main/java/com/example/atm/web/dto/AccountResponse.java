
package com.example.atm.web.dto;

import java.math.BigDecimal;

public class AccountResponse {
    private String accountNumber;
    private BigDecimal balance;

    public AccountResponse(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
}
