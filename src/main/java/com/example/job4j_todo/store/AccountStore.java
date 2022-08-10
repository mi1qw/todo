package com.example.job4j_todo.store;

import com.example.job4j_todo.model.Account;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AccountStore implements CRUDStore<Account> {
    private final Function<Function<Session, ?>, Optional<Account>> tx;
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

    /**
     * Return Account By Login And password.
     *
     * @param login    login
     * @param password password
     * @return Optional Account
     */
    public Optional<Account> findUserByLoginAndPwd(final String login, final String password) {
        return tx.apply(session -> {
            try {
                Account account = session.createQuery(
                                "from Account a where a.login=:login and "
                                + "a.password=:password",
                                Account.class)
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .uniqueResult();
                return account != null ? Optional.of(account) : Optional.empty();
            } catch (Exception e) {
                throw new IllegalStateException();
            }
        });
    }

    /**
     * find User By Login.
     *
     * @param login login
     * @return Optional Account
     */
    public Optional<Account> findUserByLogin(final String login) {
        return tx.apply(session -> {
            try {
                Account account = session.createQuery(
                                "from Account a where a.login=:login", Account.class)
                        .setParameter("login", login)
                        .uniqueResult();
                return account != null ? Optional.of(account) : Optional.empty();
            } catch (Exception e) {
                throw new IllegalStateException();
            }
        });
    }
}
