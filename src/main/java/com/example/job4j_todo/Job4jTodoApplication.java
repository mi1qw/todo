package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.model.Category;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.store.AccountStore;
import com.example.job4j_todo.store.CategoryStore;
import com.example.job4j_todo.store.ItemStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Job4jTodoApplication {
    @Autowired
    private ItemStore itemStore;
    @Autowired
    private AccountStore accountStore;
    @Autowired
    private CategoryStore categoryStore;

    public static void main(final String[] args) {
        SpringApplication.run(Job4jTodoApplication.class, args);
    }


    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            System.out.println("CommandLineRunner");
            try {
                Category personal = categoryStore.findByName("личное").get(0);
                Category work = categoryStore.findByName("работа").get(0);
                Category interests = categoryStore.findByName("интересы").get(0);
                Category warn = categoryStore.findByName("важное").get(0);

                Account vasya = new Account("Вася", "vasya@gmail.com", "111");
                accountStore.add(vasya);

                Account ann = new Account("Ann", "ann@gmail.com", "111");
                accountStore.add(ann);

                Item item1 = new Item("Наказать кота",
                        "Помыть, невозможно грязный кот и как-то "
                        + "странно воняет, обязательно "
                        + "отмыть а"
                        + " то запачкает холодильник!",
                        new Date(),
                        false, vasya);
                item1.getCategories().add(personal);
                itemStore.add(item1);

                Item item2 = new Item("Выгулять собаку",
                        "Гадит под дверью, просится на улицу. "
                        + "Соседи ругаются.",
                        new Date(),
                        false, vasya);
                item2.getCategories().add(warn);
                item2.getCategories().add(personal);
                itemStore.add(item2);

                Item item3 = new Item("Достать кота из холодильника",
                        "Простить кота и ...не забыть "
                        + "включить холодильник в "
                        + "розетку ",
                        new Date(), false, vasya);
                item3.getCategories().add(warn);
                item3.getCategories().add(personal);
                itemStore.add(item3);

                Item item4 = new Item("В гости к Васе",
                        "Навестить Васю на пару чашек чая",
                        new Date(), false, ann);
                item4.getCategories().add(work);
                item4.getCategories().add(warn);
                itemStore.add(item4);

                Item item5 = new Item("Новая причёска",
                        "Сходить к Танечке. Взять с собой журнал",
                        new Date(), false, ann);
                item5.getCategories().add(personal);
                item5.getCategories().add(interests);
                itemStore.add(item5);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
