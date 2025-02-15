package com.microservice.serviceA.controller;

import com.microservice.serviceA.entity.User;
import com.microservice.serviceA.model.UserLoginModel;
import com.microservice.serviceA.model.UserProfileModel;
import com.microservice.serviceA.service.UserService;
import com.microservice.serviceA.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<UserProfileModel> login(@RequestBody UserLoginModel user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null &&
                passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(
                    existingUser.getEmail(),
                    existingUser.getRole(),
                    existingUser.getUuid().toString());
            return ResponseEntity.ok(UserProfileModel.builder()
                    .uuid(existingUser.getUuid())
                    .email(existingUser.getEmail())
                    .fullName(existingUser.getFullName())
                    .phoneNumber(existingUser.getPhoneNumber())
                    .address(existingUser.getAddress())
                    .gender(existingUser.getGender())
                    .token(token)
                    .role(existingUser.getRole()).build());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

}
