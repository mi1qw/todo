package com.example.job4j_todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotExistingAccountValidator.class)

public @interface NotExistingAccount {
    String message() default "wrong Account";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
