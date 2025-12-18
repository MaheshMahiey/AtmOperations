
package com.example.atm;

import com.example.atm.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AtmApplicationTests {

    @Autowired
    AccountService accountService;

    @Test
    void depositAndWithdrawFlow() {
        var acc = accountService.deposit("11112222", "1234", new BigDecimal("100.00"));
        assertTrue(acc.getBalance().compareTo(new BigDecimal("10100.00")) == 0);
        acc = accountService.withdraw("11112222", "1234", new BigDecimal("50.00"));
        assertTrue(acc.getBalance().compareTo(new BigDecimal("10050.00")) == 0);
    }
}
