package com.estudo.api.security;

import com.estudo.api.logLogin.domain.LogAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private LogAutenticacaoService service;


    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;
    private Authentication logLogin;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, LogAutenticacaoService service) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        String email = "";
        String senha = "";

        final String authorization = req.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            if (credentials.startsWith("(") && credentials.endsWith(")")) {
                credentials = credentials.substring(1,credentials.length()-1);
            }
            final String[] values = credentials.split(":", 2);
            if (values.length > 1) {
                email = values[0];
                senha = values[1];
            }
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, senha, new ArrayList<>());
        logLogin = authToken;

        Authentication auth = authenticationManager.authenticate(authToken);


        return auth;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String ipAddress = req.getHeader("X-Real-IP");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        String userAgent = req.getHeader("User-Agent");
        String username = ((UserSS) auth.getPrincipal()).getUsername();
        String idSessao = salvar(username,"sucesso",ipAddress,userAgent,true);
        String token = jwtUtil.generateToken(username,idSessao);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        res.addHeader("Authorization", "Bearer " + token);
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
            logLogin.getName();
            String ipAddress = request.getHeader("X-Real-IP");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }

            String userAgent = request.getHeader("User-Agent");

            salvar(logLogin.getPrincipal().toString(),logLogin.getCredentials().toString(),ipAddress,userAgent,false);
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
    public String salvar(String login,String senha,String ip, String tipoConexao,boolean conectado){

        return service.returnId(login,senha,ip,tipoConexao,conectado);
    }
}