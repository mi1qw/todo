package com.example.job4j_todo.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroupOne.class,
        ValidationGroupTwo.class})
public interface ValidationGroupSequence {
}
