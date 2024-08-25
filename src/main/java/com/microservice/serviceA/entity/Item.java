package com.microservice.serviceA.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String itemName;
    @Column
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;
}
