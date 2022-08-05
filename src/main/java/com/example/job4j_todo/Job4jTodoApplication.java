package com.example.job4j_todo;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.persistence.AccountStore;
import com.example.job4j_todo.persistence.ItemStore;
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

    public static void main(final String[] args) {
        SpringApplication.run(Job4jTodoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            System.out.println("CommandLineRunner");
            try {
                accountStore.add(new Account("Ann", "aa", "1"));
            } catch (Exception e) {
            }

            itemStore.add(new Item("Наказать кота", "Помыть, невозможно грязный кот и как-то "
                                                             + "странно воняет, обязательно "
                                                             + "отмыть а"
                                                             + " то запачкает холодильник!",
                    LocalDate.now(),
                    false));
            itemStore.add(new Item("Выгулять собаку", "Гадит под дверью, просится на улицу. "
                                                      + "Соседи ругаются.",
                    LocalDate.now(),
                    false));
            itemStore.add(
                    new Item("Достать кота из холодильника", "Простить кота и ...не забыть "
                                                             + "включить холодильник в "
                                                             + "розетку ",
                            LocalDate.now(), false));
        };
    }
}
