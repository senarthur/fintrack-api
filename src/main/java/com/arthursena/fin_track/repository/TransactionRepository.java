package com.arthursena.fin_track.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arthursena.fin_track.model.Transaction;
import com.arthursena.fin_track.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUser(User user);
}
