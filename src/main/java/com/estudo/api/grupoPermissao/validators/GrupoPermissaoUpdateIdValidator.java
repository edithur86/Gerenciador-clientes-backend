package com.estudo.api.grupoPermissao.validators;

import com.estudo.api.grupoPermissao.domain.GrupoPermissao;
import com.estudo.api.grupoPermissao.domain.GrupoPermissaoRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GrupoPermissaoUpdateIdValidator implements GrupoPermissaoUpdateValidator<ObjectNotFoundException> {

    @Autowired
    GrupoPermissaoRepository repository;

    @Override
    public Integer getOrder() {
        return 0;
    }

    @Override
    public boolean isValid(GrupoPermissao entity) {
        Optional<GrupoPermissao> byId = repository.findById(entity.getId());
        if(byId.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public ObjectNotFoundException getException(GrupoPermissao entity) {
       return new ObjectNotFoundException("{grupoPermissao.exception.idNaoEncontrado}");
    }
}
