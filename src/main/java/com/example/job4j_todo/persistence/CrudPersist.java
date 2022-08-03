package com.example.job4j_todo.persistence;

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
    private final Function<Function<Session, T>, T> tx;

    public CrudPersist(final Class<T> aClass,
                       final Function<Function<Session, T>, T> tx) {
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
     * session.get with catch ObjectNotFoundException
     * change by get()
     *
     * @param id   id
     * @param item item
     * @return return
     */
    @Override
    public boolean replace(final Long id, final T item) {
        return (boolean) tx.apply(session -> {
            try {
                T oldItem = session.get(aClass, id);
                int i = oldItem.hashCode();
            } catch (Exception e) {
                return (T) Boolean.valueOf(false);
            }
            setIdmethod(id, item);
            session.merge(item);
            return (T) Boolean.valueOf(true);
        });
    }


    @Override
    public boolean delete(final Long id) {
        try {
            int del = (int) tx.apply(session -> {
                Integer del1 = session
                        .createQuery("delete from " + className + " where id=:id")
                        .setParameter("id", id)
                        .executeUpdate();
                return (T) del1;
            });
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
            return (List<T>) tx.apply(session -> (T) session
                    .createQuery("from " + className).list());
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }


    @Override
    public List<T> findByName(final String name) {
        try {
            List<T> list = (List<T>) tx.apply(session -> (T) session
                    .createQuery("from " + className + " a where a.name=:name", aClass)
                    .setParameter("name", name)
                    .list());
            return list;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }


    private String getNameEntity() {
        return (String) tx.apply(session -> (T) session.getMetamodel().entity(aClass).getName());
    }
}
