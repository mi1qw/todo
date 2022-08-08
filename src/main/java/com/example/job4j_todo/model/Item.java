package com.example.job4j_todo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate created;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Item(final String title, final String description, final LocalDate created,
                final boolean status) {
        this.description = description;
        this.created = created;
        this.status = status;
        this.title = title;
    }

    public Item(final String title, final String description, final LocalDate created,
                final boolean status, final Account account) {
        this.description = description;
        this.created = created;
        this.status = status;
        this.title = title;
        this.account = account;
    }
}
