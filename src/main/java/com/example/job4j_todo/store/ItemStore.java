package com.example.job4j_todo.store;

import com.example.job4j_todo.model.Item;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore implements CRUDStore<Item> {
    private final Function<Function<Session, ?>, Item> tx;
    private final CrudPersist<Item> crud;

    public ItemStore(final Function tx) {
        this.tx = tx;
        this.crud = new CrudPersist(Item.class, tx);
    }

    @Override
    public Item add(final Item item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Item item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Item> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Item> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Item findById(final Long id) {
        return crud.findById(id);
    }
}
