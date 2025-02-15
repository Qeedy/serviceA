package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);

    public User findByEmail(String email);

    @Query("FROM User u where u.role = 'TECHNICIAN'")
    public List<User> findAllTechnician();
}
