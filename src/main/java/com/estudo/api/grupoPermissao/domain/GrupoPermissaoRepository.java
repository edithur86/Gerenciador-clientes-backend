package com.estudo.api.grupoPermissao.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoPermissaoRepository extends JpaRepository<GrupoPermissao, String> {

    Optional<GrupoPermissao> findByAgrupador(String agrupador);

    Page<GrupoPermissao> findByAgrupadorOrderByAgrupadorAsc(String agrupador, Pageable pageRequest);

    Page<GrupoPermissao> findByDescricaoIgnoreCaseLike(String filter, Pageable pageRequest);

}
