package com.example.job4j_todo.controllers;

import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.persistence.ItemStore;
import com.example.job4j_todo.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemStore store;
    private final ItemService service;

    public ItemController(final ItemStore store, final ItemService service) {
        this.store = store;
        this.service = service;
    }

    @GetMapping("/{id}")
    public String getItem(final @PathVariable("id") long id, final Model model) {
        Item item = store.findById(id);
        service.setItem(item);
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(final @PathVariable("id") long id, final Model model) {
        store.delete(id);
        model.addAttribute("filtr", "all");
        return "redirect:/items";
    }

    @GetMapping("/{id}/complete")
    public String completeById(final @PathVariable("id") long id, final Model model) {
        Item item = service.getItem();
        item.setStatus(true);
        store.replace(id, item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/uncomplete")
    public String unCompleteById(final @PathVariable("id") long id) {
        Item item = service.getItem();
        item.setStatus(false);
        store.replace(id, item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editById(final @PathVariable("id") long id, final Model model) {
        Item item = service.getItem();
        if (item == null || item.getId() != id) {
            item = store.findById(id);
            service.setItem(item);
        }
        model.addAttribute("item", item);
        return "editeditem";
    }

    @PostMapping("/{id}/edit")
    public String postEditById(final @PathVariable("id") long id,
                               final @RequestParam("description") String description,
                               final @RequestParam("title") String title,
                               final Model model) {
        if (id == 0) {
            Item item = new Item(title, description, LocalDate.now(ZoneId.systemDefault()), false);
            store.add(item);
            return "redirect:/items";
        }
        Item item = service.getItem();
        if (item == null || item.getId() != id) {
            item = store.findById(id);
            if (item == null) {
                return "redirect:/items";
            }
        }
        item.setTitle(title);
        item.setDescription(description);
        service.setItem(item);
        store.replace(id, item);
        return "redirect:/items";
    }
}
