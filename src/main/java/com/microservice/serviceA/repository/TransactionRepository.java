package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
