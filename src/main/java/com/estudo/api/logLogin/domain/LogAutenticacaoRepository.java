package com.estudo.api.logLogin.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAutenticacaoRepository extends JpaRepository<LogAutenticacao,String> {
}
