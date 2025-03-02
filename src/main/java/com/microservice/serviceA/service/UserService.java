package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.User;
import com.microservice.serviceA.model.UserProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserProfileModel findByUsername(String username);
    public User findByEmail(String email);
    public User register(User user);
    public User createOrUpdateUser(User user);
    public UserProfileModel updateUser(UserProfileModel user);
    public void deleteUser(UUID uuid);
    public Page<UserProfileModel> getAllUsers(String keyword, Pageable pageable);
    public List<UserProfileModel> getAllTechnician();
}
