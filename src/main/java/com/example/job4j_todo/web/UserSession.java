package com.example.job4j_todo.web;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.model.Item;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class UserSession {
    private Item item;
    private Account account;
}
