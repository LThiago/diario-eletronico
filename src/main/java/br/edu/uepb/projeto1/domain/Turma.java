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
@Table(name = "turmas")
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sala")
    private String sala;

    @Column(name = "codigo")
    private String codigo;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private Set<Aluno> alunos;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private Set<Professor> professores;

    public Turma(String nome, String sala, String codigo) {
        this.nome = nome;
        this.sala = sala;
        this.codigo = codigo;
        this.alunos = new HashSet<>();
        this.professores = new HashSet<>();
    }
    
}