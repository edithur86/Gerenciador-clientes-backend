package com.estudo.api.grupoPermissao.domain;

import com.estudo.api.UserService;
import com.estudo.api.grupoPermissao.validators.GrupoPermissaoDeleteValidator;
import com.estudo.api.grupoPermissao.validators.GrupoPermissaoInsertValidator;
import com.estudo.api.grupoPermissao.validators.GrupoPermissaoUpdateValidator;
import com.estudo.api.resources.exceptions.BeanValidationException;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.services.exceptions.ValidatorService;
import com.estudo.api.util.IdGenerator;
import com.google.common.base.Strings;
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
public class GrupoPermissaoService {
    @Autowired
    private GrupoPermissaoRepository repository;

    @Autowired
    private GrupoUsuarioPermissaoJDBCRepository jdbcRepository;

    @Autowired
    private ValidatorService service;

    private List<? extends GrupoPermissaoInsertValidator> insertValidators;
    private List<? extends GrupoPermissaoUpdateValidator> updateValidators;
    private List<? extends GrupoPermissaoDeleteValidator> deleteValidators;

    @Autowired
    public GrupoPermissaoService(ListableBeanFactory beanFactory) {
        insertValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoPermissaoInsertValidator.class).values());
        updateValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoPermissaoUpdateValidator.class).values());
        deleteValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoPermissaoDeleteValidator.class).values());
    }

    public Page<GrupoPermissao> findAll(int page, int pageSize, String filter) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        if (Strings.isNullOrEmpty(filter)) {
            return repository.findAll(pageRequest);
        } else {
            String agrupador = null;

            filter = "%" + filter + "%";
            if (agrupador != null) {
                return repository.findByAgrupadorOrderByAgrupadorAsc(agrupador, pageRequest);
            } else{
                return repository.findByDescricaoIgnoreCaseLike(filter, pageRequest);
            }
        }
    }

    public GrupoPermissao findByAgrupador(String agrupador) {
        String[] array = {agrupador.toString()};
        return repository.findByAgrupador(agrupador).orElseThrow(() -> new ObjectNotFoundException(
                "{grupoPermissao.exception.codigoNaoEncontrado}", array));
    }


    public GrupoPermissao insert(GrupoPermissao grupoPermissao) {
        grupoPermissao.setId(IdGenerator.get());
        grupoPermissao.setDateCreate(LocalDateTime.now());
        grupoPermissao.setSessaoCreate(UserService.authenticated().getId());
        if (service.validate(grupoPermissao).ifPresents()) {
            throw new BeanValidationException(service.get());
        }
        insertValidators.sort(Comparator.comparing(GrupoPermissaoInsertValidator::getOrder));
        insertValidators.forEach(validator -> validator.validate(grupoPermissao));

        return repository.save(grupoPermissao);
    }

    public GrupoPermissao update(GrupoPermissao grupoPermissao) {
        grupoPermissao.setDateCreate(LocalDateTime.now());
        grupoPermissao.setSessaoCreate(UserService.authenticated().getId());
        if (service.validate(grupoPermissao).ifPresents()) {
            throw new BeanValidationException(service.get());
        }
        updateValidators.sort(Comparator.comparing(GrupoPermissaoUpdateValidator::getOrder));
        updateValidators.forEach(validator -> validator.validate(grupoPermissao));
        return repository.save(grupoPermissao);
    }

    public GrupoPermissao delete(String id) {
        GrupoPermissao grupoPermissao = GrupoPermissao.builder().id(id).build();
        deleteValidators.sort(Comparator.comparing(GrupoPermissaoDeleteValidator::getOrder));
        deleteValidators.forEach(validator -> validator.validate(grupoPermissao));
        repository.deleteById(grupoPermissao.getId());
        return grupoPermissao;
    }

    public Page<GrupoPermissao> findAll(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return repository.findAll(pageRequest);
    }

    public GrupoPermissao findById(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("{oai.roles.exception.idNaoEncontrado}", array));
    }

    public List<GrupoUsuarioPermissaoLstDto> findByListaGrupoPermissaoPorGrupo(String id) {
        if (id == null || id.isEmpty() || id.equals("undefined")) {
            id = "0";
        }
        List<GrupoUsuarioPermissaoLstDto> grupUserPermissao = jdbcRepository.getGrupoPermissaoPorGrupo(id);
        return grupUserPermissao;
    }
}
