package com.microservice.serviceA.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.entity.Item;
import com.microservice.serviceA.entity.Transaction;
import com.microservice.serviceA.service.CustomerService;
import com.microservice.serviceA.service.TransactionService;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction)
            throws JsonProcessingException {
        Transaction response = transactionService
                .createTransaction(transaction);
        String transactionData = objectMapper.writeValueAsString(response);
        kafkaTemplate.send("transaction", transactionData);
        return response;
    }

}
