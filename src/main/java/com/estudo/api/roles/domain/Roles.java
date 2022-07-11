package com.estudo.api.roles.domain;

import com.estudo.api.commons.EntityBase;
import com.estudo.api.grupoPermissao.domain.GrupoPermissao;
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
@Table(name = "OAI_ROLES")
public class Roles implements EntityBase {
    @Id
    @ApiModelProperty("Id Interno do Atributo Roles")
    @Column(length = 40)
    private String id;

    @ApiModelProperty("Descrição do Roles")
    @Column(length = 50)
    private String descricao;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<GrupoPermissao> grupoPermissaos;

    @NotNull(message = "{default.erro.dataInsert}")
    private LocalDateTime dateCreate;

    @NotNull(message = "{default.erro.sessaoInsert}")
    private String sessaoCreate;

    private LocalDateTime dateUpdate;

    private String sessaoUpdate;

}
