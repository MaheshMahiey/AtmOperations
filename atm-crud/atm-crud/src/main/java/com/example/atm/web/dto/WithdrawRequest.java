
package com.example.atm.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class WithdrawRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String pin;
    @NotNull
    @Positive
    private BigDecimal amount;

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
