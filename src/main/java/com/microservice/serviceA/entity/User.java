package com.microservice.serviceA.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tblUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(unique = true)
    private String username;
    @Column
    private String fullName;
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private String phoneNumber;
    @Column
    private String role;
    @Column
    private String password;
}
