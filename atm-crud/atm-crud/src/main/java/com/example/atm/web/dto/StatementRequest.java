
package com.example.atm.web.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public class StatementRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String pin;
    private Instant from;
    private Instant to;

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public Instant getFrom() { return from; }
    public void setFrom(Instant from) { this.from = from; }
    public Instant getTo() { return to; }
    public void setTo(Instant to) { this.to = to; }
}
