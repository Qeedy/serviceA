package com.microservice.serviceA.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.serviceA.entity.Transaction;
import com.microservice.serviceA.model.TransactionModel;
import org.springframework.core.io.InputStreamResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    public List<Transaction> getAllTransactions();
    public Optional<Transaction> getTransactionById(String id);
    public Transaction createTransaction(Transaction transaction) throws JsonProcessingException;
    public TransactionModel getTransactionGistory(LocalDate startDate, LocalDate endDate, String category);
    public InputStreamResource getTransactionHistoryPdf(LocalDate startDate, LocalDate endDate, String category);
    public void sendEmail(String receiver);
}
