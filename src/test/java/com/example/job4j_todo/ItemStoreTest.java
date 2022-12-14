package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.store.AccountStore;
import com.example.job4j_todo.store.ItemStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemStoreTest {
    @Autowired
    private ItemStore store;
    @Autowired
    private AccountStore accountStore;
    private Account account;

    @BeforeEach
    void setUp() {
        List<Account> accounts = accountStore.findAll();
        if (accounts.isEmpty()) {
            account = new Account("name", "login", "password");
            accountStore.add(account);
        } else {
            account = accounts.get(0);
        }
    }

    @Test
    public void add() {
        Item item = new Item("item1", "item1", new Date(), false, account);
        store.add(item);
        assertThat(item.getId() > 0).isTrue();
    }

    @Test
    public void replace() {
        Item item = new Item("item1", "item1", new Date(), false, account);
        store.add(item);
        long id = item.getId();
        assertThat(
                store.replace(id,
                        new Item("item2", "item1", new Date(), false, account))
        ).isTrue();
        item = store.findById(id);
        assertThat(item.getTitle()).isEqualTo("item2");
    }

    @Test
    public void delete() {
        Item item = new Item("item1", "item1", new Date(), false, account);
        store.add(item);
        long id = item.getId();
        assertThat(store.delete(id)).isTrue();
        assertThat(store.findById(id)).isNull();
    }

    @Test
    public void deleteNonExistingItemShouldFalse() {
        assertThat(store.delete(9999L)).isFalse();
    }

    @Test
    public void findAll() {
        List<Item> list = store.findAll();
        assertThat(list)
                .extracting("title", String.class)
                .doesNotContain("A", "B");
        store.add(new Item("A", "item1", new Date(), false, account));
        store.add(new Item("B", "item1", new Date(), false, account));
        list = store.findAll();
        assertThat(list)
                .extracting("title", String.class)
                .contains("A", "B");
    }

    @Test
    public void findByName() {
        assertThat(store.findByName("A1")).isEmpty();
        store.add(new Item("A1", "item1", new Date(), false, account));
        assertThat(store.findByName("A1")).hasSize(1);
    }

    @Test
    public void findById() {
        Item item = store.add(new Item("A", "item1", new Date(), false, account));
        long id = item.getId();
        Item item1 = store.findById(id);
        assertThat(item).isEqualTo(item1);
    }
}
