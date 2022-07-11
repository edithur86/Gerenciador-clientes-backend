package com.estudo.api.usuarios.domain;

import com.estudo.api.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private UsuarioJDBCRepository repoJDBC;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usr = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		List<String> rollesUsuario = repoJDBC.getRollesUsuario(usr.getId());
		return UserSS.builder()
				.id(usr.getId())
				.email(usr.getEmail())
				.senha(usr.getSenha())
				.authorities(rollesUsuario.stream().map(x -> new SimpleGrantedAuthority("ROLE_" + x.toUpperCase())).collect(Collectors.toList()))
			.build();
	}
}
