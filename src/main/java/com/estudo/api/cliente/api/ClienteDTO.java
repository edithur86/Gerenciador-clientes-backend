package com.estudo.api.cliente.api;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private String id;
    private String cnpjCpf;
    private String nome;
    private String bairro;
    private String cidade;
    private String endereco;

}
