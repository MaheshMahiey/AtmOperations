
package com.example.atm.web.dto;

import com.example.atm.model.Transaction;
import com.example.atm.model.TxnType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class StatementResponse {
    private List<Item> items;

    public StatementResponse(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() { return items; }

    public static StatementResponse from(List<Transaction> txns) {
        var items = txns.stream().map(t -> new Item(
                t.getId(), t.getType(), t.getAmount(), t.getBalanceAfter(), t.getCreatedAt()
        )).collect(Collectors.toList());
        return new StatementResponse(items);
    }

    public static class Item {
        private Long id;
        private TxnType type;
        private BigDecimal amount;
        private BigDecimal balanceAfter;
        private Instant createdAt;

        public Item(Long id, TxnType type, BigDecimal amount, BigDecimal balanceAfter, Instant createdAt) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public TxnType getType() { return type; }
        public BigDecimal getAmount() { return amount; }
        public BigDecimal getBalanceAfter() { return balanceAfter; }
        public Instant getCreatedAt() { return createdAt; }
    }
}
