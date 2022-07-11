package com.estudo.api.roles.validators;

import com.estudo.api.commons.validator.Validator;
import com.estudo.api.roles.domain.Roles;

public interface RolesUpdateValidator<EXCEPTION extends RuntimeException> extends Validator<Roles, EXCEPTION> {
    Integer getOrder();
}
