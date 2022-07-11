package com.estudo.api.grupo.domain;

import com.estudo.api.commons.EntityBase;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "GRUPO")
public class GrupoMin implements EntityBase, Cloneable {

    @Id
    @Setter
    @ApiModelProperty("Id Interno do Grupo de Usuário")
    private String id;

    @NotNull(message = "{oai.grupo.exception.codigoNaoInformado}")
    @ApiModelProperty("Código do Grupo de Usuário")
    @Setter
    private Integer codigo;

    @NotNull(message = "{oai.grupo.exception.descricaoNaoInformado}")
    @ApiModelProperty("Descrição do Grupo de Usuário")
    private String descricao;

    @ApiModelProperty("Grupo de usuário Ativo=true ou Desativado=false")
    private boolean ativo = true;


    @NotNull(message = "{default.erro.dataInsert}")
    @Setter
    private LocalDateTime dateCreate;

    @NotNull(message = "{default.erro.sessaoInsert}")
    @Setter
    private String sessaoCreate;

    @Setter
    private LocalDateTime dateUpdate;

    @Setter
    private String sessaoUpdate;

    @Setter
    @ApiModelProperty("Ordem do Grupo de Usuário")
    @NotNull(message = "{grupo.exception.ordemNotNull}")
    private Integer ordem;


}
