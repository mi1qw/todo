package com.example.job4j_todo.store;

import com.example.job4j_todo.model.Category;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CategoryStore implements CRUDStore<Category> {
    private final Function<Function<Session, ?>, Optional<Category>> tx;
    private final CrudPersist<Category> crud;

    public CategoryStore(final Function tx) {
        this.tx = tx;
        this.crud = new CrudPersist(Category.class, tx);
    }

    @Override
    public Category add(final Category category) {
        return crud.add(category);
    }

    @Override
    public boolean replace(final Long id, final Category category) {
        return crud.replace(id, category);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Category> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Category> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Category findById(final Long id) {
        return crud.findById(id);
    }
}
