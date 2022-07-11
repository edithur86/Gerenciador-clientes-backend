package com.estudo.api.services.exceptions;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class ValidatorService<T> {

    private Set<ConstraintViolation<T>> constraintViolations;

    public ValidatorService validate(T obj){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        constraintViolations = validator.validate(obj);
        return this;
    }

    public boolean ifPresents(){
        return constraintViolations.size() > 0;
    }

    public Set<ConstraintViolation<T>> get(){
        return this.constraintViolations;
    }
}
