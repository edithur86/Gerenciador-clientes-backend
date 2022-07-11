package com.estudo.api.cliente.domain;

import com.estudo.api.commons.EntityBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTE")
public class Cliente implements EntityBase, Cloneable {

    @Id
    @Setter
    @ApiModelProperty("Id Interno do Cliente")
    @NotNull(message = "{cliente.id.notNull}")
    private String id;

    @NotNull
    @ApiModelProperty("Código da Entidade")
    @Setter
    @Column(columnDefinition = "BIGINT")
    @NotNull(message = "{cliente.codigo.notNull}")
    private Integer codigo;

    @Setter
    @ApiModelProperty("Nome da cliente ")
    @NotNull(message = "{cliente.nome.notNull}")
    private String nome;

    @Setter
    @ApiModelProperty("CNPJ ou CPF")
    @NotNull(message = "{cliente.cnpjCpf.notNull}")
    private String cnpjCpf;

    @Setter
    @ApiModelProperty("Telefone Residencial")
    @NotNull(message = "{cliente.telResidencial.notNull}")
    private String telResidencial;

    @Setter
    @ApiModelProperty("Email")
    @NotNull(message = "{cliente.email.notNull}")
    private String email;

    @Setter
    @ApiModelProperty("cep")
    private String cep;

    @Setter
    @ApiModelProperty("Endereço")
    private String endereco;

    @Setter
    @ApiModelProperty("Numero")
    private String numero;

    @Setter
    @ApiModelProperty("Bairro")
    private String bairro;

    @Setter
    @ApiModelProperty("Cidade")
    private String cidade;

    @Setter
    @ApiModelProperty("Uf")
    private String uf;

    @Setter
    @ApiModelProperty("Cliente Ativo=true ou Desativado=false")
    private boolean ativo = true;

    @ApiModelProperty("Endereços do Cliente")
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteEndereco> enderecos = new ArrayList<>();

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

    public boolean temMesmoId(Cliente cliente) {
        return this.id.equals(cliente.getId());
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
