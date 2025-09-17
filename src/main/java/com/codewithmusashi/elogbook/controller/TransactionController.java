package com.codewithmusashi.elogbook.controller;

import com.codewithmusashi.elogbook.entity.Transaction;
import com.codewithmusashi.elogbook.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiateTransaction(@RequestBody Transaction transaction) {
        if (transaction.getBuyerType() == null || transaction.getAmount() == null || transaction.getTransactionId() == null) {
            return ResponseEntity.badRequest().body("Buyer type, amount, and transaction ID are required");
        }
        Transaction savedTransaction = transactionService.initiateTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<?> getTransactionStatus(@PathVariable String transactionId) {
        var transactionOpt = transactionService.findByTransactionId(transactionId);
        return transactionOpt.map(transaction -> ResponseEntity.ok(transaction))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<?> updateTransactionStatus(@PathVariable String transactionId, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body("Status is required");
        }
        try {
            Transaction updatedTransaction = transactionService.updateTransactionStatus(transactionId, Transaction.Status.valueOf(status));
            return ResponseEntity.ok(updatedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status or transaction not found");
        }
    }
}