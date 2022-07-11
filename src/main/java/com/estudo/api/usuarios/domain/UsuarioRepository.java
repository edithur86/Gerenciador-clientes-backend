package com.estudo.api.usuarios.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByid(String id);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByCodigo(Integer codigo);

    List<Usuario> findByGrupoUsuario_id(String id);

    Page<Usuario> findByGrupoUsuario_id(String id, Pageable pageRequest);



    @Query("SELECT coalesce(max(ch.codigo), 0) FROM Usuario ch")
    Integer getMaxCodigo();

    @Query("SELECT u from Usuario u where u.ativo = :ativo")
    Page<Usuario> findByAtivo(Pageable var1, boolean ativo);

    Page<Usuario> findByCodigoOrderByCodigoAsc(Integer codigo, Pageable pageRequest);
}
