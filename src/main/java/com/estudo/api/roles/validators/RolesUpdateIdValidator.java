package com.estudo.api.roles.validators;

import com.estudo.api.roles.domain.Roles;
import com.estudo.api.roles.domain.RolesRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RolesUpdateIdValidator implements RolesUpdateValidator<ObjectNotFoundException> {

    @Autowired
    RolesRepository repository;

    @Override
    public Integer getOrder() {
        return 0;
    }

    @Override
    public boolean isValid(Roles entity) {
        Optional<Roles> byId = repository.findById(entity.getId());
        if(byId.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public ObjectNotFoundException getException(Roles entity) {
       return new ObjectNotFoundException("{roles.exception.idNaoEncontrado}");
    }
}
