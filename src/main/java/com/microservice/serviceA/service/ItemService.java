package com.microservice.serviceA.service;

import com.microservice.serviceA.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public List<Item> getAllItems();
    public Optional<Item> getItemById(Long id);
    public Item createOrUpdateItem(Item item);
}
