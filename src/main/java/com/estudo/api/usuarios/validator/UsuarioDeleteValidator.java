package com.estudo.api.usuarios.validator;

import com.estudo.api.commons.validator.Validator;
import com.estudo.api.usuarios.domain.Usuario;

public interface UsuarioDeleteValidator <EXCEPTION extends RuntimeException> extends Validator<Usuario, EXCEPTION> {
    Integer getOrder();
}
