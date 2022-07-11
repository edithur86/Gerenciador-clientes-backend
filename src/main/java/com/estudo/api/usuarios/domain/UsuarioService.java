package com.estudo.api.usuarios.domain;

import com.google.common.base.Strings;
import com.estudo.api.UserService;
//import com.oaisoftware.api.credencial.api.UsuarioUpdateSenhaDTO;
import com.estudo.api.resources.exceptions.BeanValidationException;
import com.estudo.api.security.UserSS;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import com.estudo.api.services.exceptions.ValidatorService;
import com.estudo.api.usuarios.validator.UsuarioDeleteValidator;
import com.estudo.api.usuarios.validator.UsuarioInsertValidator;
import com.estudo.api.usuarios.validator.UsuarioUpdateValidator;
import com.estudo.api.util.IdGenerator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ValidatorService service;

    @Autowired
    private BCryptPasswordEncoder pe;

    private List<? extends UsuarioInsertValidator> insertValidators;
    private List<? extends UsuarioUpdateValidator> updateValidators;
    private List<? extends UsuarioDeleteValidator> deleteValidators;

    @Autowired
    public UsuarioService(ListableBeanFactory beanFactory) {
        insertValidators = new ArrayList<>(beanFactory.getBeansOfType(UsuarioInsertValidator.class).values());
        updateValidators = new ArrayList<>(beanFactory.getBeansOfType(UsuarioUpdateValidator.class).values());
        deleteValidators = new ArrayList<>(beanFactory.getBeansOfType(UsuarioDeleteValidator.class).values());
    }

    public Usuario insert(Usuario usuario) {

        usuario.setId(IdGenerator.get());
        usuario.setCodigo(repository.getMaxCodigo() + 1);

        usuario.setDateCreate(LocalDateTime.now());
        usuario.setSessaoCreate(UserService.authenticated().getId());

        if (service.validate(usuario).ifPresents()) {
            throw new BeanValidationException(service.get());
        }

        insertValidators.forEach(validator -> validator.validate(usuario));

        return repository.save(usuario);
    }

    public Usuario update(Usuario usuario) {

        if (service.validate(usuario).ifPresents()) {
            throw new BeanValidationException(service.get());
        }

        updateValidators.forEach(validator -> validator.validate(usuario));

        return repository.save(usuario);
    }


    public Page<Usuario> findAll(int page, int pageSize, String filter, String ativo) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
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
            return repository.findByCodigoOrderByCodigoAsc(codigo, pageRequest);
        }
    }

    public Usuario findById(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "{oai.usuario.exception.idNaoEncontrado}", array));
    }

    public Usuario findByIdInternal(String id) {
        String[] array = {id};
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "{oai.usuario.exception.idNaoEncontrado}", array));
    }

    public Usuario findByEmail(String email) {
        String[] array = {email};
        return repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(
                "{oai.usuario.exception.emailNaoEncontrado}: ", array));
    }

    public Usuario findByCodigo(Integer codigo) {
        String[] array = {codigo.toString()};
        return repository.findByCodigo(codigo).orElseThrow(() -> new ObjectNotFoundException(
                "{oai.usuario.exception.codigoNaoEncontrado}", array));
    }


    public Usuario findByMyUser() {
        UserSS user = UserService.authenticated();
        String[] array = {user.getUsername()};
        return repository.findByEmail(user.getUsername()).orElseThrow(() -> new ObjectNotFoundException(
                "{oai.usuario.exception.usuarioEmailNaoEncontrado}", array));
    }

    public Usuario delete(String id) {

        Usuario usuario = Usuario.builder().id(id).build();

        deleteValidators.sort(Comparator.comparing(UsuarioDeleteValidator::getOrder));
        deleteValidators.forEach(validator -> validator.validate(usuario));

        repository.deleteById(usuario.getId());

        return usuario;
    }
}