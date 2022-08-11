package com.example.job4j_todo.service;

import com.example.job4j_todo.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class CategoryFormatter implements Formatter<Category> {
    @Autowired
    private CategoryService categoryService;

    public CategoryFormatter() {
        super();
    }

    @Override
    public Category parse(final String text, final Locale locale) throws ParseException {
        Long id = Long.valueOf(text);
        return categoryService.findById(id);
    }

    @Override
    public String print(final Category object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}
