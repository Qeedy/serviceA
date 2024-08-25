package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    public List<Transaction> getAllTransactions();
    public Optional<Transaction> getTransactionById(String id);
    public Transaction createTransaction(Transaction transaction);
}
