package com.estudo.api.grupo.domain;

import com.google.common.base.Strings;
import com.estudo.api.UserService;
import com.estudo.api.grupo.validators.GrupoDeleteValidator;
import com.estudo.api.grupo.validators.GrupoInsertValidator;
import com.estudo.api.grupo.validators.GrupoUpdateValidator;
import com.estudo.api.resources.exceptions.BeanValidationException;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.services.exceptions.ValidatorService;
import com.estudo.api.util.IdGenerator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private ValidatorService service;

    private List<? extends GrupoInsertValidator> insertValidators;
    private List<? extends GrupoUpdateValidator> updateValidators;
    private List<? extends GrupoDeleteValidator> deleteValidators;

    @Autowired
    public GrupoService(ListableBeanFactory beanFactory) {
        insertValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoInsertValidator.class).values());
        updateValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoUpdateValidator.class).values());
        deleteValidators = new ArrayList<>(beanFactory.getBeansOfType(GrupoDeleteValidator.class).values());
    }

    public Grupo insert(Grupo grupo) {

        grupo.setId(IdGenerator.get());
        Integer maximo = repository.getMaxOrdem();
        grupo.setCodigo(repository.getMaxCodigo() + 1);

        grupo.setDateCreate(LocalDateTime.now());
        grupo.setSessaoCreate(UserService.authenticated().getId());
        grupo.setOrdem(maximo + 1);

        if (service.validate(grupo).ifPresents()) {
            throw new BeanValidationException(service.get());
        }


        insertValidators.forEach(validator -> validator.validate(grupo));

        return repository.save(grupo);

    }

    public Grupo update(Grupo grupo) {

        grupo.setDateCreate(LocalDateTime.now());
        grupo.setSessaoCreate(UserService.authenticated().getId());

        if (service.validate(grupo).ifPresents()) {
            throw new BeanValidationException(service.get());
        }

        updateValidators.forEach(validator -> validator.validate(grupo));

        return repository.save(grupo);
    }

    public Page<Grupo> findAllOrderByOrdem(int page, int pageSize, String ativo) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        if (ativo == null || ativo.isEmpty()) {
            return repository.findAllOrderByOrdem(pageRequest);
        } else {
            return repository.findByAtivoOrderByOrdem(pageRequest, Boolean.parseBoolean(ativo));
        }

    }

    public Page<Grupo> findAll(int page, int pageSize, String filter, String ativo) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by("ordem"));
        if (Strings.isNullOrEmpty(filter)) {
            if (ativo == null || ativo.isEmpty()) {
                return repository.findAll(pageRequest);
            } else {
                return repository.findByAtivo(pageRequest, Boolean.parseBoolean(ativo));
            }
        } else {
            Integer codigo = null;
            try {
                codigo = Integer.parseInt(filter);
            } catch (NumberFormatException e) {

            }
            if (filter.toUpperCase().equals("ATIVO")) {
                return repository.findByAtivo(pageRequest, true);
            } else if (filter.toUpperCase().equals("INATIVO")) {
                return repository.findByAtivo(pageRequest, false);
            }
            filter = "%" + filter + "%";
            if (codigo != null) {
                return repository.findByCodigoEAtivo(codigo, true, pageRequest);
            } else {
                return repository.findByDescricaoEAtivo(filter, true, pageRequest);
            }
        }

    }

    public Grupo findById(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "{grupo.exception.idNaoEncontrado}", array));
    }

    public Grupo findByCodigo(Integer codigo) {
        String[] array = {codigo.toString()};
        Optional<Grupo> byCodigo = repository.findByCodigo(codigo);
        if (byCodigo.isPresent()) {
            if (byCodigo.get().isAtivo()) {
                return byCodigo.get();
            } else {
                throw new ObjectNotFoundException("{grupo.exception.grupoUsuario.inativa}");
            }
        } else {
            throw new ObjectNotFoundException("{grupo.exception.codigoNaoEncontrado}", array);
        }
    }

    public Grupo delete(String id) {

        Grupo grupo = Grupo.builder().id(id).build();

        deleteValidators.sort(Comparator.comparing(GrupoDeleteValidator::getOrder));
        deleteValidators.forEach(validator -> validator.validate(grupo));

        subtrairUmOrdem(id);
        repository.deleteById(grupo.getId());

        return grupo;
    }

    private void subtrairUmOrdem(String id) {
        Optional<Grupo> grupo = repository.findById(id);
        repository.subtrairUmOrdem(grupo.get().getOrdem());
    }

    public Grupo ordenar(String idGrupo, Integer ordemDestino) {
        Grupo obj = findById(idGrupo);

        Integer maximo = repository.getMaxOrdem();
        Integer minimo = repository.getMinOrdem();

        if (obj.getOrdem() > ordemDestino && obj.getOrdem() != minimo) {
            repository.updateOrdemDestinoMaior(ordemDestino, obj.getOrdem());
            obj.setOrdem(ordemDestino);
            repository.save(obj);
        } else if (obj.getOrdem() < ordemDestino && obj.getOrdem() != maximo) {
            repository.updateOrdemDestinoMenor(ordemDestino, obj.getOrdem());
            obj.setOrdem(ordemDestino);
            repository.save(obj);
        }
        return obj;
    }

}
