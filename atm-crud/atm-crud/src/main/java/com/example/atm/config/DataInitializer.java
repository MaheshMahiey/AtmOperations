
package com.example.atm.config;

import com.example.atm.model.Account;
import com.example.atm.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(AccountRepository accounts, PasswordEncoder encoder) {
        return args -> {
            if (accounts.count() == 0) {
                Account a1 = new Account("11112222", encoder.encode("1234"), new BigDecimal("10000.00"));
                Account a2 = new Account("33334444", encoder.encode("4321"), new BigDecimal("5000.00"));
                accounts.save(a1);
                accounts.save(a2);
            }
        };
    }
}
