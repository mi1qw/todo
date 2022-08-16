package com.example.job4j_todo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailReg, String> {
    private static final Pattern PATTERN = Pattern.compile(
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)"
            + "+[a-zA-Z]{2,6}$"
    );

    @Override
    public boolean isValid(final String s,
                           final ConstraintValidatorContext context) {
        return PATTERN.matcher(s).matches();
    }
}
