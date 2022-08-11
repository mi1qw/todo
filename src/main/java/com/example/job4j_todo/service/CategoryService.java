package com.example.job4j_todo.service;

import com.example.job4j_todo.model.Category;
import com.example.job4j_todo.store.CategoryStore;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Data
@Service
@ApplicationScope
public class CategoryService {
    private Map<Long, Category> categories = new ConcurrentHashMap<>();

    public CategoryService(final CategoryStore categoryStore) {
        categoryStore.findAll()
                .forEach(n -> categories.put(n.getId(), n));
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public Category findById(final Long id) {
        return categories.get(id);
    }
}
