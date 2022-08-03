package com.example.job4j_todo;

import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.persistence.ItemStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemStoreTest {
    @Autowired
    private ItemStore store;

    @Test
    public void add() {
        Item item = new Item("item1", "item1", LocalDate.now(), false);
        store.add(item);
        assertThat(item.getId() > 0).isTrue();
    }

    @Deprecated
    public void replace() {
        Item item = new Item("item1", "item1", LocalDate.now(), false);
        store.add(item);
        long id = item.getId();
        store.replace(id, new Item("item2", "item1", LocalDate.now(), false));
        item = store.findById(id);
        assertThat(item.getDescription()).isEqualTo("item2");
    }

    @Test
    public void delete() {
        Item item = new Item("item1", "item1", LocalDate.now(), false);
        store.add(item);
        long id = item.getId();
        assertThat(store.delete(id)).isTrue();
        assertThat(store.delete(9999L)).isFalse();
        assertThat(store.findById(id)).isNull();
    }

    @Deprecated
    public void findAll() {
        store.add(new Item("A", "item1", LocalDate.now(), false));
        store.add(new Item("B", "item1", LocalDate.now(), false));
        List<Item> list = store.findAll();
        assertThat(list)
                .extracting("description", String.class)
                .containsExactly("A", "B");

    }

    @Deprecated
    public void findByName() {
        store.add(new Item("A1", "item1", LocalDate.now(), false));
        assertThat(store.findByName("A1")).hasSize(1);
        assertThat(store.findByName("A10")).isEmpty();
    }

    @Test
    public void findById() {
        Item item = store.add(new Item("A", "item1", LocalDate.now(), false));
        long id = item.getId();
        Item item1 = store.findById(id);
        assertThat(item).isEqualTo(item1);
    }
}
