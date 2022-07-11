package com.estudo.api.logLogin.domain;

import com.estudo.api.UserService;
import com.estudo.api.util.IdGenerator;
import com.estudo.api.util.ProtectedConfigFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class LogAutenticacaoService {
    @Autowired
    private LogAutenticacaoRepository repository;

    public String returnId(String usuario, String senha, String ip, String tipoConexao,boolean conect){
        LogAutenticacao logAut = LogAutenticacao.builder()
                .id(IdGenerator.get())
                .dateCreate(LocalDateTime.now())
                .sessaoCreate("sistema")
                .ipConexao(ip)
                .tipoConexao(tipoConexao)
                .conexaoRealizada(conect)
                .login(usuario)
                .senha(ProtectedConfigFile.encrypt(senha,"!*j4but1c4ba$#"))
                .build();
        repository.save(logAut);

        return logAut.getId();
    }
}
