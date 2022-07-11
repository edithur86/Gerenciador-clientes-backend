package com.estudo.api.grupo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, String> {

    List<Grupo> findByUsuarios_id(String id);

    Page<Grupo> findByCodigoOrderByCodigoAsc(Integer codigo, Pageable pageRequest);

    Page<Grupo> findByDescricaoIgnoreCaseLike(String filter, Pageable pageRequest);

    Page<Grupo> findAllOrderByOrdem(Pageable pageRequest);

    Optional<Grupo> findByCodigo(Integer codigo);

    @Query("SELECT lv from Grupo lv where lv.codigo = :codigo AND lv.ativo = :ativo")
    Page<Grupo> findByCodigoEAtivo(Integer codigo, boolean ativo, Pageable pageRequest);

    @Query("SELECT lv from Grupo lv where lv.descricao LIKE %:filter% AND lv.ativo = :ativo")
    Page<Grupo> findByDescricaoEAtivo(String filter, boolean ativo, Pageable pageRequest);


    @Query("SELECT coalesce(max(ch.codigo), 0) FROM Grupo ch")
    Integer getMaxCodigo();

    @Query("SELECT lv from Grupo lv where lv.ativo = :ativo")
    Page<Grupo> findByAtivo(Pageable var1, boolean ativo);

    Page<Grupo> findByAtivoOrderByOrdem(Pageable var1, boolean ativo);

    @Query("SELECT coalesce(max(ch.ordem), 0) FROM Grupo ch")
    Integer getMaxOrdem();

    @Query("SELECT coalesce(min(ch.ordem), 0) FROM Grupo ch")
    Integer getMinOrdem();

    @Modifying
    @Transactional
    @Query("UPDATE Grupo f set f.ordem = f.ordem - 1 where f.ordem>=:ordemOrigem AND f.ordem<=:ordemDestino")
    void updateOrdemDestinoMenor(Integer ordemDestino, Integer ordemOrigem);

    @Modifying
    @Transactional
    @Query("UPDATE Grupo f set f.ordem = f.ordem + 1 where f.ordem>=:ordemDestino AND f.ordem<=:ordemOrigem")
    void updateOrdemDestinoMaior(Integer ordemDestino, Integer ordemOrigem);

    @Modifying
    @Transactional
    @Query("UPDATE Grupo f set f.ordem = f.ordem - 1 where f.ordem>=:ordem")
    void subtrairUmOrdem(Integer ordem);
}
