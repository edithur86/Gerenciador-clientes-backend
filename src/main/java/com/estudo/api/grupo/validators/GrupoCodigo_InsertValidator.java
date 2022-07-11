package com.estudo.api.grupo.validators;

import com.estudo.api.grupo.domain.Grupo;
import com.estudo.api.grupo.domain.GrupoRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
    public class GrupoCodigo_InsertValidator implements GrupoInsertValidator<ObjectNotFoundException> {
    @Autowired
    private GrupoRepository repository;

    private String codigo;

    @Override
    public boolean isValid(Grupo grupo) {

        Optional<Grupo> byCodigo = repository.findByCodigo(grupo.getCodigo());
        if (byCodigo.isPresent()) {
            this.codigo = byCodigo.get().getCodigo().toString();
            return false;
        }
        return true;
    }

    @Override
    public ObjectNotFoundException getException(Grupo grupo) {
        throw new ObjectNotFoundException("{grupo.exception.codigoDuplicado}", new String[]{this.codigo});
    }
}
