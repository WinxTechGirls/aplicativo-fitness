package com.generation.fitness.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity 
@Table(name= "tb_niveis")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Deve selecionar o nivel para continuar")
    private String dificuldade;


    @OneToMany(fetch = FetchType.LAZY , mappedBy = "nivel" , cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("nivel")
    private List<Produto> produto;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDificuldade() {
        return dificuldade;
    }


    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }


    public List<Produto> getProduto() {
        return produto;
    }


    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    } 






}