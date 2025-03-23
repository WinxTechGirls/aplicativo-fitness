package com.generation.fitness.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.fitness.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	//Controle de versão para essa classe

	private String userName; // para receber o email
	private String password; // para receber a senha
	private List<GrantedAuthority> authorities;
	// Vai trabalhar com as autorizações que o usuário tem.
	
	// Método construtor
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}

	public UserDetailsImpl() {	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() { //Retornar a senha

		return password;
	}

	@Override
	public String getUsername() { 

		return userName;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}