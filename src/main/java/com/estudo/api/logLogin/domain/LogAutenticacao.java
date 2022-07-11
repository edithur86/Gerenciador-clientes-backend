package com.estudo.api.logLogin.domain;

import com.estudo.api.commons.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "log_autenticacao")
public class LogAutenticacao implements EntityBase {
    @Id
    @NotNull(message = "{LogAutenticacao.exception.idNotNull}")
    private String id;
    @NotNull(message = "{LogAutenticacao.exception.loginNotNull}")
    private String login;
    @NotNull(message = "{LogAutenticacao.exception.senhaNotNull}")
    private String senha;
    @NotNull(message = "{LogAutenticacao.exception.ipConexaoNotNull}")
    private String ipConexao;
    @NotNull(message = "{LogAutenticacao.exception.tipoConexaoNotNull}")
    private String tipoConexao;
    @NotNull(message = "{LogAutenticacao.exception.conexaoRealizadaNotNull}")
    private boolean conexaoRealizada;



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
}
