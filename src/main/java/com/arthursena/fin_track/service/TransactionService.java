package com.arthursena.fin_track.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.User;
import com.arthursena.fin_track.model.dto.TransactionResponse;
import com.arthursena.fin_track.model.enums.TransactionType;
import com.arthursena.fin_track.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) { 
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> findAllTransactions() {
        User user = getCurrentUser();
        return transactionRepository.findByUser(user).stream().map(TransactionResponse::new).toList().reversed();
    }

    public Optional<TransactionResponse> findTransactionById(String id) {
        User user = getCurrentUser();

        return transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .map(TransactionResponse::new);
    }

    public TransactionResponse saveTransaction(Transaction transaction) {
        User user = getCurrentUser();
        
        transaction.setUser(user);
        transaction.setId(null);
        Transaction newTransaction = transactionRepository.save(transaction);
        return new TransactionResponse(newTransaction);
    }

    public void deleteTransaction(String id) {
        User user = getCurrentUser();
        transactionRepository.findById(id)
            .filter(t -> t.getUser().getId().equals(user.getId()))
            .ifPresent(transactionRepository::delete);
    }

    public TransactionResponse updateTransaction(String id, Transaction updatedTransaction) {
        User user = getCurrentUser();
        
        return transactionRepository.findById(id)
            .filter(t -> t.getUser().getId().equals(user.getId()))
            .map(transaction -> {
                transaction.setDescription(updatedTransaction.getDescription());
                transaction.setTitle(updatedTransaction.getTitle());
                transaction.setTransactionType(updatedTransaction.getTransactionType());
                transaction.setAmount(updatedTransaction.getAmount());

                Transaction saved = transactionRepository.save(transaction);
                return new TransactionResponse(saved);
            }).orElse(null);
    }

    public BigDecimal calculateBalance() {
        User user = getCurrentUser();

        List<Transaction> transactions = transactionRepository.findByUser(user);
        
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

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
