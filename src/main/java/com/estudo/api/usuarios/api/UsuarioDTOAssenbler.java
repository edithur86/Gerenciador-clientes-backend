package com.estudo.api.usuarios.api;

import com.estudo.api.usuarios.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDTOAssenbler {

    public UsuarioDTO fromEntity(Usuario entity) {
        return UsuarioDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .usuario(entity.getUsuario())
                .email(entity.getEmail())
                .dateCreate(entity.getDateCreate())
                .sessaoCreate(entity.getSessaoCreate())
                .dateUpdate(entity.getDateUpdate())
                .sessaoUpdate(entity.getSessaoUpdate())
                .build();
    }

    public List<UsuarioDTO> fromEntity(List<Usuario> entity) {
        List<UsuarioDTO> retorno = new ArrayList<>();
        for (Usuario ent : entity) {
            retorno.add(fromEntity(ent));
        }
        return retorno;
    }
}
