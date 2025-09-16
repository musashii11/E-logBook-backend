package com.codewithmusashi.elogbook.repository;

import java.util.List;
import com.codewithmusashi.elogbook.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    List<Transaction> findByBuyerId(Long buyerId);
    List<Transaction> findByCollegeId(Long collegeId);
}