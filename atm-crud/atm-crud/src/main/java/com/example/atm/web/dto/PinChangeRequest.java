
package com.example.atm.web.dto;

import jakarta.validation.constraints.NotBlank;

public class PinChangeRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String oldPin;
    @NotBlank
    private String newPin;

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getOldPin() { return oldPin; }
    public void setOldPin(String oldPin) { this.oldPin = oldPin; }
    public String getNewPin() { return newPin; }
    public void setNewPin(String newPin) { this.newPin = newPin; }
}
