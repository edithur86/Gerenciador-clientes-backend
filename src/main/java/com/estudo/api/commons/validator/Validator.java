package com.estudo.api.commons.validator;


import com.estudo.api.commons.EntityBase;

public interface Validator<ENTITY extends EntityBase, EXCEPTION extends RuntimeException> {

    boolean isValid(ENTITY entity);

    EXCEPTION getException(ENTITY entity);

    default void validate(ENTITY entity) {
        if (!isValid(entity)) {
            throw getException(entity);
        }
    }
}
