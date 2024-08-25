package com.microservice.serviceA.repository;

import com.microservice.serviceA.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
