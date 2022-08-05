package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.persistence.AccountStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

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
        Account account = new Account("name", "login1", "password");
        store.add(account);
        long id = account.getId();
        store.replace(id, new Account("name2", "login1", "password"));
        account = store.findById(id);
        assertThat(account.getName()).isEqualTo("name2");
    }

    @Test
    public void delete() {
        Account account = new Account("name", "login2", "password");
        store.add(account);
        long id = account.getId();
        assertThat(store.delete(id)).isTrue();
        assertThat(store.delete(9999L)).isFalse();
        assertThat(store.findById(id)).isNull();
    }

    @Test
    public void findAll() {
        List<Account> listBefore = store.findAll();
        store.add(new Account("A", "login3", "password"));
        store.add(new Account("B", "login4", "password"));
        List<Account> list = store.findAll();
        List<String> listItems = listBefore.stream().map(Account::getName)
                .collect(Collectors.toList());
        listItems.add("A");
        listItems.add("B");
        String[] arrayItems = new String[listItems.size()];
        listItems.toArray(arrayItems);
        assertThat(list)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder(arrayItems);

    }

    @Test
    public void findByName() {
        store.add(new Account("A1", "login5", "password"));
        assertThat(store.findByName("A1")).hasSize(1);
        assertThat(store.findByName("A10")).isEmpty();
    }

    @Test
    public void findById() {
        Account account = store.add(new Account("A", "login6", "password"));
        long id = account.getId();
        Account account1 = store.findById(id);
        assertThat(account).isEqualTo(account1);
    }
}
