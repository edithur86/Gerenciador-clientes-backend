package com.estudo.api.roles.domain;

import com.estudo.api.UserService;
import com.estudo.api.resources.exceptions.BeanValidationException;
import com.estudo.api.roles.validators.RolesDeleteValidator;
import com.estudo.api.roles.validators.RolesInsertValidator;
import com.estudo.api.roles.validators.RolesUpdateValidator;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.services.exceptions.ValidatorService;
import com.estudo.api.util.IdGenerator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository repository;

    @Autowired
    private ValidatorService service;

    private List<? extends RolesInsertValidator> insertValidators;
    private List<? extends RolesUpdateValidator> updateValidators;
    private List<? extends RolesDeleteValidator> deleteValidators;

    @Autowired
    public RolesService(ListableBeanFactory beanFactory) {
        insertValidators = new ArrayList<>(beanFactory.getBeansOfType(RolesInsertValidator.class).values());
        updateValidators = new ArrayList<>(beanFactory.getBeansOfType(RolesUpdateValidator.class).values());
        deleteValidators = new ArrayList<>(beanFactory.getBeansOfType(RolesDeleteValidator.class).values());
    }

    public Roles insert(Roles roles) {
        roles.setId(IdGenerator.get());
        roles.setDateCreate(LocalDateTime.now());
        roles.setSessaoCreate(UserService.authenticated().getId());
        if (service.validate(roles).ifPresents()) {
            throw new BeanValidationException(service.get());
        }
        insertValidators.sort(Comparator.comparing(RolesInsertValidator::getOrder));
        insertValidators.forEach(validator -> validator.validate(roles));

        return repository.save(roles);
    }

    public Roles update(Roles roles) {
        roles.setDateCreate(LocalDateTime.now());
        roles.setSessaoCreate(UserService.authenticated().getId());
        if (service.validate(roles).ifPresents()) {
            throw new BeanValidationException(service.get());
        }
        updateValidators.sort(Comparator.comparing(RolesUpdateValidator::getOrder));
        updateValidators.forEach(validator -> validator.validate(roles));
        return repository.save(roles);
    }

    public Roles delete(String id) {
        Roles roles = Roles.builder().id(id).build();
        deleteValidators.sort(Comparator.comparing(RolesDeleteValidator::getOrder));
        deleteValidators.forEach(validator -> validator.validate(roles));
        repository.deleteById(roles.getId());
        return roles;
    }

    public Page<Roles> findAll(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return repository.findAll(pageRequest);
    }

    public Roles findById(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("{roles.exception.idNaoEncontrado}", array));
    }

}
