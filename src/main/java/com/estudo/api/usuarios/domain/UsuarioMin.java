package com.estudo.api.usuarios.domain;

import com.estudo.api.commons.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class UsuarioMin implements EntityBase, Cloneable {

    @Id
    @Setter
    private String id;

    @Setter
    @NotNull(message = "{oai.usuario.exception.codigoNaoInformado}")
    private Integer codigo;

    @NotNull(message = "{oai.usuario.exception.usuarioNaoInformado}")
    private String usuario;

    private String email;

    @JsonIgnore
    @Setter
    private String senha;

    private boolean ativo = true;

    @NotNull(message = "{oai.default.erro.dataInsert}")
    @Setter
    private LocalDateTime dateCreate;

    @NotNull(message = "{oai.default.erro.sessaoInsert}")
    @Setter
    private String sessaoCreate;

    @Setter
    private LocalDateTime dateUpdate;

    @Setter
    private String sessaoUpdate;


}
