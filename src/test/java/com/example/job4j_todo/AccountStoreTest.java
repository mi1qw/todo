package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.persistence.AccountStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountStoreTest {
    @Autowired
    private AccountStore store;

    @Test
    public void add() {
        Account account = new Account("name", "login", "password");
        store.add(account);
        assertThat(account.getId() > 0).isTrue();
    }

    @Test
    public void replace() {
        Account account = new Account("name", "login", "password");
        store.add(account);
        long id = account.getId();
        store.replace(id, new Account("name2", "login", "password"));
        account = store.findById(id);
        assertThat(account.getName()).isEqualTo("name2");
    }

    @Test
    public void delete() {
        Account account = new Account("name", "login", "password");
        store.add(account);
        long id = account.getId();
        assertThat(store.delete(id)).isTrue();
        assertThat(store.delete(9999L)).isFalse();
        assertThat(store.findById(id)).isNull();
    }

    @Test
    public void findAll() {
        store.add(new Account("A", "login", "password"));
        store.add(new Account("B", "login", "password"));
        List<Account> list = store.findAll();
        assertThat(list)
                .extracting("name", String.class)
                .containsExactly("A", "B");

    }

    @Test
    public void findByName() {
        store.add(new Account("A1", "login", "password"));
        assertThat(store.findByName("A1")).hasSize(1);
        assertThat(store.findByName("A10")).isEmpty();
    }

    @Test
    public void findById() {
        Account account = store.add(new Account("A", "login", "password"));
        long id = account.getId();
        Account account1 = store.findById(id);
        assertThat(account).isEqualTo(account1);
    }
}
