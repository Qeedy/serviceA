package com.microservice.serviceA.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ItemCategory {
    @Id
    private String id;
    @Column(unique = true)
    private String categoryName;
}
