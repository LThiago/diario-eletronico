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
@Table(name = "professores")
public class Professor extends User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "matricula", unique = true)
    private String matricula;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "formacao")
    private String formacao;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "professor_turma", joinColumns = @JoinColumn(name = "professor_id"), inverseJoinColumns = @JoinColumn(name = "turma_id"))
    @JsonIgnore
    private Set<Turma> turmas;

    @OneToOne
    private Projeto projeto;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_projeto")
    private PapelProjeto papel_projeto;

    public Professor(String nome, String formacao, String matricula, String email, String username, String password) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.username = username;
        this.password = password;
        this.formacao = formacao;
        this.turmas = new HashSet<>();
        this.projeto = null;
        this.papel_projeto = null;
    }

    public Professor(User user) {
        this.nome = user.getNome();
        this.matricula = user.getMatricula();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.formacao = null;
        this.turmas = new HashSet<>();
        this.projeto = null;
        this.papel_projeto = null;
    }

}