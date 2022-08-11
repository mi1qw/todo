package com.example.job4j_todo.controller;

import com.example.job4j_todo.model.Category;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.service.UserSession;
import com.example.job4j_todo.store.ItemStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemStore store;
    private final UserSession session;

    public ItemController(final ItemStore store, final UserSession session) {
        this.store = store;
        this.session = session;
    }

    @GetMapping("/{id}")
    public String getItem(final @PathVariable("id") long id, final Model model) {
        Item item = store.findById(id);
        session.setItem(item);
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
        Item item = session.getItem();
        item.setStatus(true);
        store.replace(id, item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/uncomplete")
    public String unCompleteById(final @PathVariable("id") long id) {
        Item item = session.getItem();
        item.setStatus(false);
        store.replace(id, item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editById(final @PathVariable("id") long id, final Model model) {
        Item item = session.getItem();
        if (item == null || item.getId() != id) {
            item = store.findById(id);
            session.setItem(item);
        }
        model.addAttribute("item", item);
        return "editeditem";
    }

    @PostMapping("/{id}/edit")
    public String postEditById(final @PathVariable("id") long id,
                               final @RequestParam("description") String description,
                               final @RequestParam("title") String title,
                               final @RequestParam List<Category> categories,
                               final Model model,
                               final @ModelAttribute Item itemForm) {
        Long idForm = itemForm.getId();
        if (idForm == 0) {
            Item item = new Item(title, description, LocalDate.now(ZoneId.systemDefault()),
                    false, session.getAccount());
            itemForm.setCreated(LocalDate.now(ZoneId.systemDefault()));
            itemForm.setAccount(session.getAccount());
            itemForm.setId(null);
            session.setItem(itemForm);
            store.add(itemForm);
            return "redirect:/items";
        }
        Item item = session.getItem();
        if (item == null || item.getId() != id) {
            item = store.findById(id);
            if (item == null) {
                return "redirect:/items";
            }
        }
        itemForm.setAccount(session.getAccount());
        itemForm.setCreated(item.getCreated());
        session.setItem(itemForm);
        store.replace(id, itemForm);
        return "redirect:/items";
    }
}
