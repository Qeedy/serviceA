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

import java.util.List;
import java.util.UUID;

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
    public UserProfileModel updateUser(UserProfileModel user) {
        User data = userRepository.findByUuid(user.getUuid());
        if(data == null)
            throw new BsaeException("User doesn't exist");
        data.setAddress(user.getAddress());
        data.setEmail(user.getEmail());
        data.setPhoneNumber(user.getPhoneNumber());
        data.setGender(user.getGender());
        data.setFullName(user.getFullName());
        return constructUserProfile(userRepository.save(data));
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }


    @Override
    public Page<UserProfileModel> getAllUsers(String keyword, Pageable pageable) {
        return userRepository.findAllByKeyword(keyword, pageable)
                .map(this::constructUserProfile);
    }

    @Override
    public List<UserProfileModel> getAllTechnician() {
        return userRepository.findAllTechnician()
                .stream().map(this::constructUserProfile)
                .toList();
    }

    @Override
    public User register(User user) {
        User data = userRepository.findByEmail(user.getEmail());
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
                .role(user.getRole())
                .gender(user.getGender())
                .build();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
