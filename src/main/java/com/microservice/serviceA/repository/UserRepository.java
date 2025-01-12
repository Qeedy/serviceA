package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);

    public User findByEmail(String email);
}
