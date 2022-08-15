package com.example.job4j_todo.service;


import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.store.ItemStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemStore itemStore;

    public ItemService(final ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public List<Item> findByUser(final Account account) {
        return itemStore.findByUser(account);
    }

    public Item findById(final Long id) {
        return itemStore.findById(id);
    }

    public boolean delete(final Long id) {
        return itemStore.delete(id);
    }

    public boolean replace(final Long id, final Item item) {
        return itemStore.replace(id, item);
    }

    public Item add(final Item item) {
        return itemStore.add(item);
    }
}
