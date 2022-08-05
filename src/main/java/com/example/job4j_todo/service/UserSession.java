package com.example.job4j_todo.service;

import com.example.job4j_todo.model.Item;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Service
@SessionScope
public class UserSession {
    private Item item;
    private String name;
}
