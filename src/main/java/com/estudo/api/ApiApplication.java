package com.estudo.api;

import com.estudo.api.cliente.domain.ClienteRepository;
import com.estudo.api.usuarios.domain.Usuario;
import com.estudo.api.usuarios.domain.UsuarioRepository;
import com.estudo.api.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlauto;


    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        List<Usuario> listUsuairo = usuarioRepository.findAll();
        if (listUsuairo.isEmpty()) {
            Usuario user1 = Usuario.builder()
                    .id(IdGenerator.get())
                    .codigo(-1)
                    .email("admin@admin.com.br")
                    .usuario("admin")
                    .ativo(true)
                    .dateCreate(LocalDateTime.now())
                    .sessaoCreate("1")
                    .senha(pe.encode("admin"))
                    .build();
            usuarioRepository.save(user1);
        }


    }
}


