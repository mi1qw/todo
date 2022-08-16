package com.example.job4j_todo.controller;

import com.example.job4j_todo.model.Item;
import com.example.job4j_todo.service.ItemService;
import com.example.job4j_todo.validation.ValidationGroupSequence;
import com.example.job4j_todo.web.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final UserSession session;

    public ItemController(final ItemService itemService, final UserSession session) {
        this.itemService = itemService;
        this.session = session;
    }

    @GetMapping("/{id}")
    public String getItem(final @PathVariable("id") Long id, final Model model) {
        Item item = itemService.findById(id);
        if (!isValidItemByUser(item)) {
            return "redirect:/items";
        }
        session.setItem(item);
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(final @PathVariable("id") Long id, final Model model) {
        Item item = itemService.findById(id);
        if (!isValidItemByUser(item)) {
            return "redirect:/items";
        }
        itemService.delete(id);
        model.addAttribute("filtr", "all");
        return "redirect:/items";
    }

    @GetMapping("/updateStatus/{id}")
    public String updateStatusById(final @PathVariable("id") Long id,
                                   final @RequestParam("status") boolean status) {
        Item item = session.getItem();
        if (!isValidItemByUserById(item, id)) {
            return "redirect:/items";
        }
        item.setStatus(status);
        itemService.replace(id, item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editById(final @PathVariable("id") Long id, final Model model) {
        Item item = session.getItem();
        if (item == null || !item.getId().equals(id)) {
            item = itemService.findById(id);
            session.setItem(item);
        }
        if (!isValidItemByUserById(item, id)) {
            return "redirect:/items";
        }
        model.addAttribute("item", item);
        return "editeditem";
    }

    @PostMapping("/{id}/edit")
    public String postEditById(final @PathVariable("id") Long id,
                               final @Validated(ValidationGroupSequence.class)
                               @ModelAttribute Item itemForm,
                               final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editeditem";
        }
        Long idForm = itemForm.getId();
        if (idForm == 0) {
            itemForm.setCreated(LocalDate.now(ZoneId.systemDefault()));
            itemForm.setAccount(session.getAccount());
            itemForm.setId(null);
            itemService.add(itemForm);
            session.setItem(itemForm);
            return "redirect:/items";
        }
        Item item = session.getItem();
        if (item == null || !item.getId().equals(id)) {
            item = itemService.findById(id);
            if (item == null) {
                return "redirect:/items";
            }
        }
        if (!isValidItemByUser(item)) {
            return "redirect:/items";
        }
        itemForm.setAccount(session.getAccount());
        itemForm.setCreated(item.getCreated());
        session.setItem(itemForm);
        itemService.replace(id, itemForm);
        return "redirect:/items";
    }

    private boolean isValidItemByUser(final Item item) {
        return session.getAccount() != null
               && session.getAccount().equals(item.getAccount());
    }

    private boolean isValidItemByUserById(final Item item, final Long id) {
        return isValidItemByUser(item) && item.getId().equals(id);
    }
}
