package com.example.job4j_todo;

import com.example.job4j_todo.persistence.ItemStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Job4jTodoApplication {
    @Autowired
    private ItemStore itemStore;

    public static void main(final String[] args) {
        SpringApplication.run(Job4jTodoApplication.class, args);
    }

/*    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            System.out.println("CommandLineRunner");
            itemStore.add(new Item("Помыть кота", "Невозможно грязный кот",
                    LocalDate.now(),
                    false));
            itemStore.add(new Item("Выгулять собаку", "Гадит под дверью, просится на улицу",
                    LocalDate.now(),
                    false));
            itemStore.add(new Item("Достать кота из холодильника", "У кота лапы в собачьем г-не",
                    LocalDate.now(), false));
        };
    }*/
}
