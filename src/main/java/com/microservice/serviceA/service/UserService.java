package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.User;
import com.microservice.serviceA.model.UserProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public UserProfileModel findByUsername(String username);
    public User findByEmail(String email);
    public User register(User user);
    public User createOrUpdateUser(User user);
    public Page<UserProfileModel> getAllUsers(Pageable pageable);
    public List<UserProfileModel> getAllTechnician();
}
