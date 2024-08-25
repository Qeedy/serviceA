package com.microservice.serviceA.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String customerName;
    @Column
    private String customerAddress;
    @Column(columnDefinition = "NUMERIC(19,2) default 0.00")
    private BigDecimal balance;
}
