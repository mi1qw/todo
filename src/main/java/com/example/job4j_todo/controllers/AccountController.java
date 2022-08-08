package com.example.job4j_todo.controllers;

import com.example.job4j_todo.model.Account;
import com.example.job4j_todo.persistence.AccountStore;
import com.example.job4j_todo.service.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccountController {
    private final AccountStore accountStore;
    private final UserSession session;

    public AccountController(final AccountStore accountStore, final UserSession session) {
        this.accountStore = accountStore;
        this.session = session;
    }

    @GetMapping("/signPage")
    public String loginPage(final Model model,
                            final @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "signin";
    }

    @PostMapping("/signIn")
    public String signIn(final @ModelAttribute Account account, final Model model) {
        boolean isCorrect = true;
        if (!validEmail(account.getLogin())
            || accountStore.findUserByLogin(account.getLogin()).isPresent()) {
            model.addAttribute("failLogin", true);
            isCorrect = false;
        }
        if (account.getPassword().length() < 3) {
            model.addAttribute("failPassword", true);
            isCorrect = false;
        }
        if (account.getName().length() < 2) {
            model.addAttribute("failName", true);
            isCorrect = false;
        }
        if (isCorrect) {
            Account newAccount = accountStore.add(
                    new Account(account.getName(), account.getLogin(), account.getPassword()));
            newAccount.setLogin(null);
            newAccount.setPassword(null);
            session.setAccount(newAccount);
            return "redirect:/items";
        }
        model.addAttribute("fail", true);
        model.addAttribute(account);
        return "signin";
    }

    @PostMapping("/logIn")
    public String login(final @ModelAttribute Account account) {
        Optional<Account> userDb = accountStore.findUserByLoginAndPwd(
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

    private boolean validEmail(final String email) {
        String regex
                = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)"
                  + "+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }
}
