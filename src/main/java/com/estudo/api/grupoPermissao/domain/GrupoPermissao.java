package com.estudo.api.grupoPermissao.domain;

import com.estudo.api.commons.EntityBase;
import com.estudo.api.grupo.domain.Grupo;
import com.estudo.api.roles.domain.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OAI_GRUPO_PERMISSAO")
public class GrupoPermissao implements EntityBase {
    @Id
    @ApiModelProperty("Id Interno do Atributo Grupo permissão")
    @Column(length = 40)
    private String id;

    @ApiModelProperty("Descrição do Grupo permissão")
    @Column(length = 50)
    private String descricao;

    @ApiModelProperty("Agrupador do Grupo permissão")
    @Column(length = 50)
    private String agrupador;

    @ManyToMany
    @JoinTable(name = "GRUPO_PERMISSAO_ROLES",
            joinColumns = {@JoinColumn(name = "ID_GRUPO_PERMISSAO")},
            inverseJoinColumns = {@JoinColumn(name = "ID_ROLES")})
    private List<Roles> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "grupoPermissaos")
    private List<Grupo> grupos;

    @NotNull(message = "{default.erro.dataInsert}")
    private LocalDateTime dateCreate;

    @NotNull(message = "{default.erro.sessaoInsert}")
    private String sessaoCreate;

    private LocalDateTime dateUpdate;

    private String sessaoUpdate;

}
