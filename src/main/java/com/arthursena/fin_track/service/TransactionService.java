package com.arthursena.fin_track.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.User;
import com.arthursena.fin_track.model.dto.PageTransactionResponse;
import com.arthursena.fin_track.model.dto.TransactionResponse;
import com.arthursena.fin_track.model.enums.TransactionType;
import com.arthursena.fin_track.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) { 
        this.transactionRepository = transactionRepository;
    }

    public PageTransactionResponse findAllTransactions(int page, int size) {
        User user = getCurrentUser();
        Page<Transaction> transactionsPage = transactionRepository.findByUser(user, PageRequest.of(page, size, Direction.DESC, "createdAt"));
        List<TransactionResponse> transactions = transactionsPage.get().map(TransactionResponse::new).collect(Collectors.toList());
        
        return new PageTransactionResponse(transactions, transactionsPage.getNumber(), transactionsPage.getSize(), transactionsPage.getTotalElements());
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

        BigDecimal totalRevenues = transactionRepository.sumAmountByUserAndTransactionType(user, TransactionType.REVENUE);
        BigDecimal totalExpenses = transactionRepository.sumAmountByUserAndTransactionType(user, TransactionType.EXPENSE);

        BigDecimal revenues = totalRevenues != null ? totalRevenues : BigDecimal.ZERO;
        BigDecimal expenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;

        return revenues.subtract(expenses);
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
