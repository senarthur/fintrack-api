package com.arthursena.fin_track.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.enums.TransactionType;

public record TransactionResponse(
    String id,
    String title, 
    String description, 
    BigDecimal amount,
    TransactionType transactionType,
    Instant createdAt
) { 
    public TransactionResponse(Transaction transaction) {
        this(transaction.getId(), transaction.getTitle(), transaction.getDescription(), transaction.getAmount(), transaction.getTransactionType() ,transaction.getCreatedAt());
    }
 } 
