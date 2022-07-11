package com.estudo.api.usuarios.api;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String id;
    private Integer codigo;
    private String descricao;
    private String usuario;
    private String email;

    private LocalDateTime dateCreate;

    private String sessaoCreate;

    private LocalDateTime dateUpdate;

    private String sessaoUpdate;
}
