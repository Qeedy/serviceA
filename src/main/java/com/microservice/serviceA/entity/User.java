package com.microservice.serviceA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblUser")
public class User {
    @Id
    @Column
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
