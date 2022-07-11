package com.estudo.api.cliente.validators;

import com.estudo.api.cliente.domain.Cliente;
import com.estudo.api.commons.validator.Validator;

public interface ClienteInsertValidator<EXCEPTION extends RuntimeException> extends Validator<Cliente, EXCEPTION> {
    Integer getOrder();
}
