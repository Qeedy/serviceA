package com.microservice.serviceA.controller;


import com.microservice.serviceA.model.UserProfileModel;
import com.microservice.serviceA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileModel> getUserProfile(
            @RequestHeader("username") String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserProfileModel>> getUserList(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

}
