package com.microservice.serviceA.controller;

import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{username}")
    public Customer getCustomer(@PathVariable("username") String username) {
        return customerService.findByUsername(username);
    }
    @PostMapping
    public Customer createOrUpdateCustomer(@RequestBody Customer customer) {
        return customerService.createOrUpdateCustomer(customer);
    }
}
