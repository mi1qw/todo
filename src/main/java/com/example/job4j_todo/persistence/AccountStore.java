package com.example.job4j_todo.persistence;

import com.example.job4j_todo.model.Account;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class AccountStore implements CRUDStore<Account> {
    private final Function<Function<Session, ?>, Account> tx;
    private final CrudPersist<Account> crud;

    public AccountStore(final Function tx) {
        this.tx = tx;
        this.crud = new CrudPersist(Account.class, tx);
    }

    @Override
    public Account add(final Account account) {
        return crud.add(account);
    }

    @Override
    public boolean replace(final Long id, final Account account) {
        return crud.replace(id, account);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Account> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Account> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Account findById(final Long id) {
        return crud.findById(id);
    }
}