package com.example.job4j_todo.persistence;

import java.util.List;

public interface CRUDStore<T> {
    T add(T item);

    boolean replace(Long id, T item);

    boolean delete(Long id);

    List<T> findAll();

    List<T> findByName(String name);

    T findById(Long id);
}
