package com.arthursena.fin_track.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arthursena.fin_track.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
