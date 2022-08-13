package com.example.job4j_todo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String title;
    private String description;
    private LocalDate created;
    private boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

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
