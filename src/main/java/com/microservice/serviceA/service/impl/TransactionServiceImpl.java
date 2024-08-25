package com.microservice.serviceA.service.impl;

import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.entity.Item;
import com.microservice.serviceA.entity.Transaction;
import com.microservice.serviceA.repository.CustomerRepository;
import com.microservice.serviceA.repository.TransactionRepository;
import com.microservice.serviceA.service.TransactionService;
import com.microservice.serviceA.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        Customer cust = customerRepository.findByUsername(JwtUtil.getCurrentUsername());
        BigDecimal totalCost = transaction.getItems().stream().map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal customerChange = cust.getBalance().subtract(totalCost);
        transaction.setCustomerChange(customerChange);
        transaction.setLastBalance(cust.getBalance());
        transaction.setCustomer(cust);
        transaction.setTotalCost(totalCost);
        cust.setBalance(customerChange);
        transactionRepository.save(transaction);
        customerRepository.save(cust);
        return transaction;
    }
}
