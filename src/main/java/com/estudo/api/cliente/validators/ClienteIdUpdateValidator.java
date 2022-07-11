package com.estudo.api.cliente.validators;

import com.estudo.api.cliente.domain.Cliente;
import com.estudo.api.cliente.domain.ClienteRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteIdUpdateValidator implements ClienteUpdateValidator<ObjectNotFoundException> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public boolean isValid(Cliente entity) {
        Optional<Cliente> byId = repository.findById(entity.getId());
        if (byId.isPresent()) {
            entity.setDateCreate(byId.get().getDateCreate());
            entity.setSessaoCreate(byId.get().getSessaoCreate());
        }
        return byId.isPresent();
    }

    @Override
    public ObjectNotFoundException getException(Cliente entity) {
        String[] array = { entity.getId() };
        throw new ObjectNotFoundException("{exception.cadastroNaoEncontradoID}", array);
    }
    @Override
    public Integer getOrder() {
        return 0;
    }
}
