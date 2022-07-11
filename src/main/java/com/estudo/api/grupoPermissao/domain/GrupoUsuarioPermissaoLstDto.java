package com.estudo.api.grupoPermissao.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoUsuarioPermissaoLstDto {

    private String id;
    private String agrupador;
    private String descricao;
    private String valor;

}
