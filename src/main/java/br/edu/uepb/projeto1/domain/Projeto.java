package br.edu.uepb.projeto1.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projetos")
public class Projeto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private Professor coordenador;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private Set<Aluno> alunos;

    public Projeto(String nome, String descricao, Professor coordenador) {
        this.nome = nome;
        this.descricao = descricao;
        this.coordenador = coordenador;
        this.alunos = new HashSet<>();
    }
    
}