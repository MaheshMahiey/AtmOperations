
package com.example.atm.web;

import com.example.atm.model.Transaction;
import com.example.atm.service.AccountService;
import com.example.atm.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final AccountService accountService;

    public ATMController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@Valid @RequestBody DepositRequest request) {
        var acc = accountService.deposit(request.getAccountNumber(), request.getPin(), request.getAmount());
        return ResponseEntity.ok(new AccountResponse(acc.getAccountNumber(), acc.getBalance()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        var acc = accountService.withdraw(request.getAccountNumber(), request.getPin(), request.getAmount());
        return ResponseEntity.ok(new AccountResponse(acc.getAccountNumber(), acc.getBalance()));
    }

    @PostMapping("/pin/change")
    public ResponseEntity<MessageResponse> changePin(@Valid @RequestBody PinChangeRequest request) {
        accountService.changePin(request.getAccountNumber(), request.getOldPin(), request.getNewPin());
        return ResponseEntity.ok(new MessageResponse("PIN changed successfully"));
    }

    @PostMapping("/statement")
    public ResponseEntity<StatementResponse> statement(@Valid @RequestBody StatementRequest request) {
        List<Transaction> txns = accountService.getStatement(request.getAccountNumber(), request.getPin(), request.getFrom(), request.getTo());
        return ResponseEntity.ok(StatementResponse.from(txns));
    }
}
