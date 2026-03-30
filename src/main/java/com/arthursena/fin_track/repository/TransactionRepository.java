package com.arthursena.fin_track.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.User;
import com.arthursena.fin_track.model.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findByUser(User user, Pageable pageable);
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.transactionType = :transactionType")
    BigDecimal sumAmountByUserAndTransactionType(@Param("user") User user, @Param("transactionType") TransactionType transactionType);
}
