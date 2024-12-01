package com.microservice.serviceA.service.impl;

import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.entity.User;
import com.microservice.serviceA.exceptions.BsaeException;
import com.microservice.serviceA.repository.UserRepository;
import com.microservice.serviceA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User register(User user) {
        User data = userRepository.findByUsername(user.getUsername());
        if(data != null)
            throw new BsaeException("User exist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
