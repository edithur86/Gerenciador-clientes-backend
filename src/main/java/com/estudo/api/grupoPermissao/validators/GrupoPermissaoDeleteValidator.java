package com.estudo.api.grupoPermissao.validators;

import com.estudo.api.commons.validator.Validator;
import com.estudo.api.grupoPermissao.domain.GrupoPermissao;

public interface GrupoPermissaoDeleteValidator<EXCEPTION extends RuntimeException> extends Validator<GrupoPermissao, EXCEPTION> {
    Integer getOrder();
}
