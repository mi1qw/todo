package com.example.job4j_todo.model;

import com.example.job4j_todo.validation.EmailReg;
import com.example.job4j_todo.validation.NotExistingAccount;
import com.example.job4j_todo.validation.ValidationGroupOne;
import com.example.job4j_todo.validation.ValidationGroupTwo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@NotExistingAccount(message = "Такой пользователь уже зарегистрирован.",
        groups = {ValidationGroupTwo.class})
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 40, message = "Минимум два символа.",
            groups = ValidationGroupOne.class)
    private String name;

    @NotBlank(message = "Введите Ваш email")
    @EmailReg(message = "Неверный формат email.",
            groups = ValidationGroupOne.class)
    @Column(unique = true)
    private String login;

    @NotBlank
    @Size(min = 3, max = 40, message = "Минимум три символа.",
            groups = ValidationGroupOne.class)
    private String password;

    public Account(final String name, final String login, final String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
