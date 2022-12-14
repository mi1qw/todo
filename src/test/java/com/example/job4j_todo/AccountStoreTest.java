package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.store.AccountStore;
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
        Account account2 = new Account("name2", "login1", "password");
        assertThat(store.replace(id, account2)).isTrue();
        account = store.findById(id);
        assertThat(account.getName()).isEqualTo("name2");
    }

    @Test
    public void replaceNonExistingItemShouldFalse() {
        assertThat(store.replace(99999L,
                new Account("name2", "login1", "password"))
        ).isFalse();
        assertThat(store.findById(99999L)).isNull();
    }

    @Test
    public void delete() {
        Account account = new Account("name", "login2", "password");
        store.add(account);
        long id = account.getId();
        assertThat(store.delete(id)).isTrue();
        assertThat(store.findById(id)).isNull();
    }

    @Test
    public void deleteNonExistingItemShouldFalse() {
        assertThat(store.delete(9999L)).isFalse();
    }


    @Test
    public void findAll() {
        List<Account> listBefore = store.findAll();
        assertThat(listBefore)
                .extracting("name", String.class)
                .doesNotContain("A", "B");
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
        assertThat(store.findByName("A1")).isEmpty();
        store.add(new Account("A1", "login5", "password"));
        assertThat(store.findByName("A1")).hasSize(1);
    }

    @Test
    public void findById() {
        Account account = store.add(new Account("A", "login6", "password"));
        long id = account.getId();
        Account account1 = store.findById(id);
        assertThat(account).isEqualTo(account1);
    }
}
