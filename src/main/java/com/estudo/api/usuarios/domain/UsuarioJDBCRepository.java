package com.estudo.api.usuarios.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "UsuarioJDBCRepository")
public class UsuarioJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getRollesUsuario(String idUsuario) {
        String sql = "select ORoles.descricao from usuario_grupo UG inner join grupo G on UG.id_grupo = G.id " +
                "        inner join grupo_usuario_permissao GUP on GUP.id_grupo_usuario=G.id " +
                "        inner join oai_grupo_permissao OGP on OGP.id = GUP.id_grupo_permissao " +
                "        inner join grupo_permissao_roles GPR on GPR.id_grupo_permissao = OGP.id " +
                "        inner join oai_roles ORoles on ORoles.id = GPR.id_roles" +
                "      where UG.id_usuario = ? "+
                "      group by ORoles.descricao";

        return jdbcTemplate.query(sql, new Object[]{idUsuario}, rs -> {
            List<String> oRetorno = new ArrayList<>();
            while (rs.next()) {
                oRetorno.add(rs.getString("descricao"));
            }
            return oRetorno;
        });
    }
    public boolean getRollesUsuarioRole(String idUsuario) {
        String sql = "select ORoles.descricao from usuario_grupo UG inner join grupo G on UG.id_grupo = G.id " +
                "        inner join grupo_usuario_permissao GUP on GUP.id_grupo_usuario=G.id " +
                "        inner join oai_grupo_permissao OGP on OGP.id = GUP.id_grupo_permissao " +
                "        inner join grupo_permissao_roles GPR on GPR.id_grupo_permissao = OGP.id " +
                "        inner join oai_roles ORoles on ORoles.id = GPR.id_roles" +
                "      where UG.id_usuario = ? and ORoles.descricao = 'CONTRATO_UPDATE_VENCIMENTO' "+
                "      group by ORoles.descricao";

        return jdbcTemplate.query(sql, new Object[]{idUsuario}, rs -> {
            List<String> oRetorno = new ArrayList<>();
            while (rs.next()) {
                oRetorno.add(rs.getString("descricao"));
            }
            if(oRetorno.isEmpty()){
                return false;
            }
            return true;
        });
    }
}