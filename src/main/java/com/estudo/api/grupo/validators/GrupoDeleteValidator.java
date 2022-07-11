package com.estudo.api.grupo.validators;

import com.estudo.api.commons.validator.Validator;
import com.estudo.api.grupo.domain.Grupo;

public interface GrupoDeleteValidator<EXCEPTION extends RuntimeException> extends Validator<Grupo, EXCEPTION> {
    Integer getOrder();
}