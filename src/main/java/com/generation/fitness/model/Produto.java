package com.generation.fitness.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome é obrigatório")
	@Size(min = 5, max = 100, message = "O nome tem que ser maior que 5 e menor que 100")
	private String nome;
	
	@NotBlank(message = "O atributo tipo de assinatura é obrigatório")
	@Size(min = 5, max = 1000, message = "O o tipo de assinatura tem que ser maior que 5 e menor que 1000")
	private String tipoAssinatura;
	
	@UpdateTimestamp
	private LocalDateTime inicio;
	
	private LocalDateTime tempoEstimado;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Nivel nivel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("produto")
    private List<Usuario> usuario;

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

	public String getTipoAssinatura() {
		return tipoAssinatura;
	}

	public void setTipoAssinatura(String tipoAssinatura) {
		this.tipoAssinatura = tipoAssinatura;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(LocalDateTime tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}
	
}


