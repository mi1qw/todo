package com.example.job4j_todo.controller;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.service.AccountService;
import com.example.job4j_todo.validation.ValidationGroupSequence;
import com.example.job4j_todo.web.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final UserSession session;

    public AccountController(final AccountService accountService, final UserSession session) {
        this.accountService = accountService;
        this.session = session;
    }

    @GetMapping("/signPage")
    public String loginPage(final Model model,
                            final @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("account", new Account());
        return "signin";
    }

    @PostMapping("/signIn")
    public String signIn(
            final @Validated(ValidationGroupSequence.class)
            @ModelAttribute("account") Account account,
            final BindingResult bindingResult,
            final Model model) {
        if (bindingResult.hasErrors()) {
            return "signin";
        }
        Account newAccount = accountService.add(account);
        newAccount.setLogin(null);
        newAccount.setPassword(null);
        session.setAccount(newAccount);
        return "redirect:/items";
    }

    @PostMapping("/logIn")
    public String login(final @ModelAttribute Account account) {
        Optional<Account> userDb = accountService.findUserByLoginAndPwd(
                account.getLogin(), account.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/items?fail=true";
        }
        Account user = userDb.get();
        user.setPassword(null);
        user.setLogin(null);
        session.setAccount(user);
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(final Model model) {
        session.setAccount(null);
        return "redirect:/items";
    }
}
