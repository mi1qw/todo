package com.example.job4j_todo.controller;

import com.example.job4j_todo.model.Category;
import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.service.CategoryService;
import com.example.job4j_todo.store.ItemStore;
import com.example.job4j_todo.service.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Controller
public class ListItemsController {
    private final ItemStore store;
    private final UserSession session;
    private final CategoryService categoryService;

    public ListItemsController(final ItemStore store, final UserSession session,
                               final CategoryService categoryService) {
        this.store = store;
        this.session = session;
        this.categoryService = categoryService;
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

        Set<Category> categories = item.getCategories();
        List<Long> longs = categories.stream().map(Category::getId).toList();

        Category category = categoryService.getAllCategories().get(0);
        item.getCategories().add(category);
        item.getCategories().add(categoryService.getAllCategories().get(1));
        model.addAttribute("item", item);
        return "editeditem";
    }
}
