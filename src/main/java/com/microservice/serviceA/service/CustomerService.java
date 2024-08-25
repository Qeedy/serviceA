package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.Customer;

public interface CustomerService {
    public Customer findByUsername(String username);
    public Customer findById(String id);
    public Customer register(Customer customer);
    public Customer createOrUpdateCustomer(Customer customer);
}
