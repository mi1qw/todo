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

import java.time.LocalDate;

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

                Account account = new Account("Вася", "mail@gmail.com", "111");
                accountStore.add(account);

                Item item1 = new Item("Наказать кота",
                        "Помыть, невозможно грязный кот и как-то "
                        + "странно воняет, обязательно "
                        + "отмыть а"
                        + " то запачкает холодильник!",
                        LocalDate.now(),
                        false, account);
                item1.getCategories().add(personal);
                itemStore.add(item1);

                Item item2 = new Item("Выгулять собаку",
                        "Гадит под дверью, просится на улицу. "
                        + "Соседи ругаются.",
                        LocalDate.now(),
                        false, account);
                item2.getCategories().add(warn);
                item2.getCategories().add(personal);
                itemStore.add(item2);

                Item item3 = new Item("Достать кота из холодильника",
                        "Простить кота и ...не забыть "
                        + "включить холодильник в "
                        + "розетку ",
                        LocalDate.now(), false, account);
                item3.getCategories().add(warn);
                item3.getCategories().add(personal);
                itemStore.add(item3);


            } catch (Exception e) {
            }
        };
    }
}
