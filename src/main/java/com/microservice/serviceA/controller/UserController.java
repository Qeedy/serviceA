package com.microservice.serviceA.controller;


import com.microservice.serviceA.model.UserProfileModel;
import com.microservice.serviceA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserProfileModel>> getUserList(
            @RequestParam(value = "keyword", required = false) String keyword, Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(keyword, pageable));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserProfileModel> updateUserProfile(@RequestBody UserProfileModel userProfile) {
        return ResponseEntity.ok(userService.updateUser(userProfile));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable("uuid") UUID uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/list-technician")
    public ResponseEntity<List<UserProfileModel>> getTechnicianList() {
        return ResponseEntity.ok(userService.getAllTechnician());
    }

}
