package com.estudo.api.grupo.domain;

import com.estudo.api.commons.EntityBase;
import com.estudo.api.grupoPermissao.domain.GrupoPermissao;
import com.estudo.api.usuarios.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GRUPO")
public class Grupo implements EntityBase, Cloneable {

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

    @JsonIgnore
    @ManyToMany(mappedBy = "grupoUsuario")
    private List<Usuario> usuarios;

    @ManyToMany
    @Setter
    @JoinTable(name = "GRUPO_USUARIO_PERMISSAO",
            joinColumns = {@JoinColumn(name = "ID_GRUPO_USUARIO")},
            inverseJoinColumns = {@JoinColumn(name = "ID_GRUPO_PERMISSAO")})
    private List<GrupoPermissao> grupoPermissaos;

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

    public GrupoMin getMin(){
        return GrupoMin.builder()
                .id(this.id)
                .codigo(this.codigo)
                .descricao(this.descricao)
                .build();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return id.equals(grupo.id) &&
                codigo.equals(grupo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
