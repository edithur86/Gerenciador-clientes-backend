package com.estudo.api.resources.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class BeanValidationException extends ConstraintViolationException {


    private String message;

    public BeanValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }

    public BeanValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
