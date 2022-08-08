package com.example.job4j_todo.controllers;

import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.persistence.ItemStore;
import com.example.job4j_todo.service.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
public class ItemsController {
    private final ItemStore store;
    private final UserSession session;

    public ItemsController(final ItemStore store, final UserSession session) {
        this.store = store;
        this.session = session;
    }

    @GetMapping("/")
    public String getItems(final Model model) {
        model.addAttribute("items", store.findAll());
        return "items";
    }

    @GetMapping("/{filr}")
    public String getItems(final @PathVariable("filr") String flt, final Model model) {
        List<Item> items = switch (flt) {
            case "yes" -> store.findAll().stream().filter(Item::isStatus).toList();
            case "no" -> store.findAll().stream().filter(n -> !n.isStatus()).toList();
            default -> store.findAll();
        };
        model.addAttribute("items", items);
        model.addAttribute("filtr", flt);
        return "items";
    }

    @GetMapping("/newItem")
    public String newItem(final Model model) {
        Item item = new Item("Name", "Description",
                LocalDate.now(ZoneId.systemDefault()),
                false,
                session.getAccount());
        item.setId(0L);
        model.addAttribute("item", item);
        return "editeditem";
    }
}
