package com.estudo.api.cliente.domain;

import com.estudo.api.commons.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTE_ENDERECO")
public class ClienteEndereco implements EntityBase, Cloneable {
    @Id
    @Setter
    private String id;

    @JsonIgnore
    @ManyToOne
    @Setter
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @NotNull
    private String cep;

    private String endereco;

    private String bairro;

    private String numero;

    @NotNull
    private String cidade;

    @NotNull
    private String uf;

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
