package com.estudo.api.cliente.domain;

import com.estudo.api.UserService;
import com.estudo.api.cliente.validators.ClienteInsertValidator;
import com.estudo.api.cliente.validators.ClienteUpdateValidator;
import com.estudo.api.resources.exceptions.BeanValidationException;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.services.exceptions.ValidatorService;
import com.estudo.api.util.IdGenerator;
import com.google.common.base.Strings;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ValidatorService service;

    private List<? extends ClienteInsertValidator> insertValidators;
    private List<? extends ClienteUpdateValidator> updateValidators;

    @Autowired
    public ClienteService(ListableBeanFactory beanFactory) {
        insertValidators = new ArrayList<>(beanFactory.getBeansOfType(ClienteInsertValidator.class).values());
        updateValidators = new ArrayList<>(beanFactory.getBeansOfType(ClienteUpdateValidator.class).values());
    }

    public Cliente insert(Cliente cliente) {

        cliente.setId(IdGenerator.get());
        cliente.setCodigo(repository.getMaxCodigo() + 1);
        cliente.setDateCreate(now());
        cliente.setSessaoCreate(UserService.authenticated().getId());

        if (service.validate(cliente).ifPresents()) {
            throw new BeanValidationException(service.get());
        }
        insertValidators.sort(Comparator.comparing(ClienteInsertValidator::getOrder));
        insertValidators.forEach(validator -> validator.validate(cliente));
        return repository.save(cliente);
    }


    public Cliente update(Cliente cliente) {


        cliente.setDateUpdate(now());
        cliente.setSessaoCreate(UserService.authenticated().getId());

        if (service.validate(cliente).ifPresents()) {
            throw new BeanValidationException(service.get());
        }

        updateValidators.sort(Comparator.comparing(ClienteUpdateValidator::getOrder));
        updateValidators.forEach(validator -> validator.validate(cliente));

        return repository.save(cliente);
    }


    public Page<Cliente> findAll(int page, int pageSize, String filter) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by("codigo"));
        if (Strings.isNullOrEmpty(filter)) {
            return repository.findAll(pageRequest);
        } else {
            Integer codigo = null;
            try {
                codigo = Integer.parseInt(filter);
            } catch (NumberFormatException e) {

            }
            filter = "%" + filter + "%";
            if (codigo != null) {
                return repository.findByCodigoOrderByCodigoAsc(codigo, pageRequest);
            } else {
                return repository.findByNomeIgnoreCaseLike(filter, pageRequest);
            }
        }
    }

    public Cliente findById(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "{exception.IdNaoEncontrado}", array));
    }

    public Cliente findByCnpjCPF(String cnpjCpf) {
        String[] array = {cnpjCpf};
        return repository.findByCnpjCpf(cnpjCpf).orElseThrow(() -> new ObjectNotFoundException(
                "{exception.NaoEncontradoCPFCNPJ}", array));
    }
}
