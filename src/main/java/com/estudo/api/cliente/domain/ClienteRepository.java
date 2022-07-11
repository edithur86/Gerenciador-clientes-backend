package com.estudo.api.cliente.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findById(String id);

    Optional<Cliente> findByCodigo(Integer codigo);

    Cliente findByid(String id);

    Optional<Cliente> findByCnpjCpf(String cnpjCpf);

    Page<Cliente> findByNomeIgnoreCaseLike(String filter, Pageable pageRequest);

    Page<Cliente> findByCodigoOrderByCodigoAsc(Integer codigo, Pageable pageRequest);

    @Query("SELECT coalesce(max(ch.codigo), 0) FROM Cliente ch")
    Integer getMaxCodigo();


}
