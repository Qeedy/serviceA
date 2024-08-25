package com.microservice.serviceA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ItemCategory {
    @Id
    private String id;
    @Column(unique = true)
    private String categoryName;
}
