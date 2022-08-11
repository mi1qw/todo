package com.example.job4j_todo.store;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class CrudPersist<T> implements CRUDStore<T> {
    private final Class<T> aClass;
    private final String className;
    private Method setId;
    private final Function<Function<Session, ?>, T> tx;

    public CrudPersist(final Class<T> aClass,
                       final Function<Function<Session, ?>, T> tx) {
        this.tx = tx;
        this.aClass = aClass;
        this.className = aClass.getSimpleName();
        setMethodSetId();
    }

    private void setMethodSetId() {
        try {
            setId = aClass.getDeclaredMethod("setId", Long.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void setIdmethod(final Long id, final T obj) {
        try {
            setId.invoke(obj, id);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T add(final T item) {
        return tx.apply(session -> {
            session.persist(item);
            return item;
        });
    }

    /**
     * replace.
     *
     * @param id   id
     * @param item item
     * @return return
     */
    @Override
    public boolean replace(final Long id, final T item) {
        return (boolean) tx.apply(session -> {
            T oldItem = session.get(aClass, id);
            if (oldItem == null) {
                return false;
            }
            setIdmethod(id, item);
            session.merge(item);
            return true;
        });
    }


    @Override
    public boolean delete(final Long id) {
        try {
            int del = (int) tx.apply(session ->
                    session.createQuery("delete from " + className + " where id=:id")
                            .setParameter("id", id)
                            .executeUpdate());
            return del != 0;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public T findById(final Long id) {
        try {
            return tx.apply(
                    session -> session
                            .createQuery("from " + className + " where id=:id", aClass)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<T> findAll() {
        try {
            return (List<T>) tx.apply(session -> session
                    .createQuery("from " + className).list());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    @Override
    public List<T> findByName(final String name) {
        try {
            return (List<T>) tx.apply(session -> session
                    .createNativeQuery("select * from " + className + " a where a.name=:name",
                            aClass)
                    .setParameter("name", name)
                    .list());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    private String getNameEntity() {
        return (String) tx.apply(session -> session.getMetamodel().entity(aClass).getName());
    }
}
