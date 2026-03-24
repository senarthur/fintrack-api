package com.arthursena.fin_track.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.dto.TransactionResponse;
import com.arthursena.fin_track.service.TransactionService;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String id) {
        return transactionService.findTransactionById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody Transaction transaction) {
        TransactionResponse newTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(newTransaction);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable String id, @RequestBody Transaction updatedTransaction) {
        TransactionResponse transaction = transactionService.updateTransaction(id, updatedTransaction);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance() {
        BigDecimal balance = transactionService.calculateBalance();
        return ResponseEntity.ok(balance);
    }
    
} 
