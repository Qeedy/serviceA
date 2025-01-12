package com.microservice.serviceA.service.impl;

import com.microservice.serviceA.entity.User;
import com.microservice.serviceA.exceptions.BsaeException;
import com.microservice.serviceA.model.UserProfileModel;
import com.microservice.serviceA.repository.UserRepository;
import com.microservice.serviceA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<UserProfileModel> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::constructUserProfile);
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
    public UserProfileModel findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return constructUserProfile(user);
    }

    private UserProfileModel constructUserProfile(User user) {
        return UserProfileModel.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .build();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
