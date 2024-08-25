package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    public Customer findByUsername(String username);
}
