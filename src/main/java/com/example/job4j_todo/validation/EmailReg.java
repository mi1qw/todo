package com.example.job4j_todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)

public @interface EmailReg {
    String message() default "wrong Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
