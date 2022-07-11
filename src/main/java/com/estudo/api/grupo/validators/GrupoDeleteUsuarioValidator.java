package com.estudo.api.grupo.validators;

import com.estudo.api.grupo.domain.Grupo;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.usuarios.domain.Usuario;
import com.estudo.api.usuarios.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoDeleteUsuarioValidator implements GrupoDeleteValidator<ObjectNotFoundException> {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public boolean isValid(Grupo entity) {
        List<Usuario> byId = repository.findByGrupoUsuario_id(entity.getId());
        if (byId.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ObjectNotFoundException getException(Grupo entity) {
        String[] array = {entity.getId()};
        throw new ObjectNotFoundException("{grupo.exception.grupoJaCadastradoNoUsuario}");
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

}
