package com.estudo.api.cliente.validators;

import com.estudo.api.cliente.domain.Cliente;
import com.estudo.api.cliente.domain.ClienteRepository;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteCodigoUniqueValidator implements  ClienteInsertValidator<ObjectNotFoundException>, ClienteUpdateValidator<ObjectNotFoundException> {
    @Autowired
    private ClienteRepository repository;

    @Override
    public boolean isValid(Cliente cliente) {
        return repository.findByCodigo(cliente.getCodigo())
                .map(cliente::temMesmoId)
                .orElse(true);
    }

    @Override
    public ObjectNotFoundException getException(Cliente cliente) {
        return new ObjectNotFoundException("{oai.banco.exception.codigoDuplicado}", new String[]{cliente.getCodigo().toString()});
    }

    @Override
    public Integer getOrder() {
        return 2;
    }
}
