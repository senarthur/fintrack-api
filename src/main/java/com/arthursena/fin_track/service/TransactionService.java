package com.arthursena.fin_track.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.enums.TransactionType;
import com.arthursena.fin_track.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) { 
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }

    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        return transactionRepository.findById(id).map(transaction -> {
            transaction.setDescription(updatedTransaction.getDescription());
            transaction.setAmount(updatedTransaction.getAmount());
            transaction.setTransactionType(updatedTransaction.getTransactionType());
            transaction.setTitle(updatedTransaction.getTitle());
            return transactionRepository.save(transaction);
        }).orElse(null);
    }

    public BigDecimal calculateBalance() {
        List<Transaction> transactions = transactionRepository.findAll();
        
        BigDecimal revenues = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.REVENUE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal expenses = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return revenues.subtract(expenses);
    }
}
