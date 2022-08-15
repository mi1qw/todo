package com.example.job4j_todo.service;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.store.AccountStore;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountStore accountStore;

    public AccountService(final AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    public Optional<Account> findUserByLogin(final String login) {
        return accountStore.findUserByLogin(login);
    }

    public Account add(final Account account) {
        return accountStore.add(account);
    }

    public Optional<Account> findUserByLoginAndPwd(final String login, final String password) {
        return accountStore.findUserByLoginAndPwd(login, password);
    }
}
