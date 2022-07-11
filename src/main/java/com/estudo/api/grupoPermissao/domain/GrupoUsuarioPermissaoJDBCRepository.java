package com.estudo.api.grupoPermissao.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "GrupoUsuarioPermissaoJDBCRepository")
public class GrupoUsuarioPermissaoJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<GrupoUsuarioPermissaoLstDto> getGrupoPermissaoPorGrupo(String idGrupo) {
        String sql = "Select OGP1.id, OGP1.agrupador, OGP1.descricao, COALESCE(Permissao.id, 0 ) from oai_grupo_permissao OGP1 left join (select OGP.id from grupo_usuario_permissao GUP inner join oai_grupo_permissao OGP on OGP.id = GUP.id_grupo_permissao where GUP.id_grupo_usuario = ?) Permissao ON Permissao.id = OGP1.id order by OGP1.agrupador, OGP1.descricao;";

        return jdbcTemplate.query(sql, new Object[]{idGrupo}, rs -> {
            List<GrupoUsuarioPermissaoLstDto> oRetorno = new ArrayList<>();
            while (rs.next()) {
                oRetorno.add(GrupoUsuarioPermissaoLstDto.builder()
                        .id(rs.getString(1))
                        .agrupador(rs.getString(2))
                        .descricao(rs.getString(3))
                        .valor(rs.getString(4))
                        .build());
            }
            return oRetorno;
        });
    }

    public int insertGrupoUsuarioAdmin(String idGrupo, String idGrupoPermisao) {
        return jdbcTemplate.update(
                "INSERT INTO grupo_usuario_permissao (id_grupo_usuario, id_grupo_permissao) VALUES (?, ?)", idGrupo, idGrupoPermisao);
    }

    public void deleteTabelasPermissao() {
        jdbcTemplate.execute(
                "DELETE from grupo_permissao_roles");

        jdbcTemplate.execute(
                "DELETE from oai_roles");

        jdbcTemplate.execute(
                "DELETE from grupo_usuario_permissao");

        jdbcTemplate.execute(
                "DELETE from oai_grupo_permissao");
    }

}