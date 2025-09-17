package com.codewithmusashi.elogbook.service;

import com.codewithmusashi.elogbook.entity.Transaction;
import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.TransactionRepository;
import com.codewithmusashi.elogbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction initiateTransaction(Transaction transaction) {
        if ("STUDENT".equals(transaction.getBuyerType().name()) && transaction.getBuyer() != null) {
            Optional<User> user = userRepository.findById(transaction.getBuyer().getId());
            if (user.isEmpty() || !"STUDENT".equals(user.get().getRole().name())) {
                throw new IllegalArgumentException("Invalid student buyer");
            }
        } else if ("COLLEGE".equals(transaction.getBuyerType().name()) && transaction.getCollege() == null) {
            throw new IllegalArgumentException("College ID is required for college transactions");
        }
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(Transaction.Status.PENDING);
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    public Transaction updateTransactionStatus(String transactionId, Transaction.Status status) {
        Optional<Transaction> transactionOpt = transactionRepository.findByTransactionId(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            transaction.setStatus(status);
            return transactionRepository.save(transaction);
        }
        throw new IllegalArgumentException("Transaction not found");
    }
}