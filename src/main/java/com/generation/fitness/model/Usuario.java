package com.generation.fitness.model;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atributo nome é obrigatorio")
	private String nome;

	@NotBlank(message = "O atributo usuario é obrigatorio")
	@Size(min = 5, max = 200, message = "O usuario tem que ser maior que 4 e menor que 200 caracteres")
	@Email(message = "O Atributo Usuário deve ser um email válido!")
	private String usuario;

	@NotBlank(message = "O Atributo Senha é Obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;
	
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "usuario" , cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Produto> produto;

	private String foto;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public List<Produto> getProduto() {
		return produto;
	}
	
	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
}