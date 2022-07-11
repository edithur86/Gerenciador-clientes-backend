package com.estudo.api.usuarios.domain;

import com.estudo.api.commons.EntityBase;
import com.estudo.api.grupo.domain.GrupoMin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

//import com.oaisoftware.api.contrato.domain.Contrato;
//import com.oaisoftware.api.entidade.domain.Entidade;
//import com.oaisoftware.api.grupo.domain.Grupo;
//import com.oaisoftware.api.grupo.domain.GrupoMin;
//import com.oaisoftware.api.unidadeadministrativa.domain.UnidadeAdministrativa;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario implements EntityBase, Cloneable {

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

    @ManyToMany
    @JoinTable(name = "USUARIO_GRUPO",
            joinColumns = {@JoinColumn(name = "ID_USUARIO")},
            inverseJoinColumns = {@JoinColumn(name = "ID_GRUPO")})
    private List<GrupoMin> grupoUsuario;

    @Setter
    private LocalDateTime dateUpdate;

    @Setter
    private String sessaoUpdate;



    public boolean temMesmoId(Usuario usuario) {
        return this.id.equals(usuario.getId());
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

    public UsuarioMin getUsuarioMim() {
        return UsuarioMin.builder()
                .id(this.id)
                .codigo(this.codigo)
                .usuario(this.usuario)
                .email(this.email)
                .senha(this.senha)
                .ativo(this.ativo)
                .dateCreate(this.dateCreate)
                .sessaoCreate(this.sessaoCreate)
                .dateUpdate(this.dateUpdate)
                .sessaoUpdate(this.sessaoUpdate).build();

    }
}
