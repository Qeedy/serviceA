package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.entity.User;

public interface UserService {
    public User findByUsername(String username);
    public User register(User user);
    public User createOrUpdateUser(User user);
}
