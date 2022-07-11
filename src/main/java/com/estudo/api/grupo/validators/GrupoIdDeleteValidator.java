package com.estudo.api.grupo.validators;

import com.estudo.api.grupo.domain.Grupo;
import com.estudo.api.grupo.domain.GrupoRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GrupoIdDeleteValidator implements GrupoDeleteValidator<ObjectNotFoundException> {

    @Autowired
    private GrupoRepository repository;

    @Override
    public boolean isValid(Grupo entity) {
        Optional<Grupo> byId = repository.findById(entity.getId());
        if(byId.isPresent()){
            entity = byId.get();
        }
        return byId.isPresent();
    }

    @Override
    public ObjectNotFoundException getException(Grupo entity) {
        String[] array = { entity.getId() };
        throw new ObjectNotFoundException("{grupo.exception.idNaoEncontrado}", array);
    }

    @Override
    public Integer getOrder() {
        return 1;
    }
}
