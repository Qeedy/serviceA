package com.microservice.serviceA.service.impl;

import com.microservice.serviceA.entity.Item;
import com.microservice.serviceA.entity.ItemCategory;
import com.microservice.serviceA.repository.ItemCategoryRepository;
import com.microservice.serviceA.repository.ItemRepository;
import com.microservice.serviceA.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
    @Override
    public Item createOrUpdateItem(Item item) {
        Optional<ItemCategory> itemCategory = itemCategoryRepository.findById(item.getCategory().getId());
        if(itemCategory.isEmpty())
            itemCategoryRepository.save(item.getCategory());
        return itemRepository.save(item);
    }
}
