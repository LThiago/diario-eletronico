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
@Table(name = "alunos")
public class Aluno extends User {
    
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

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "aluno_turma", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "turma_id"))
    @JsonIgnore
    private Set<Turma> turmas;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "projeto_id")
    @JsonIgnore
    private Projeto projeto;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_projeto")
    private PapelProjeto papelProjeto;

    public Aluno(String nome, String matricula, String email, String username, String password) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.username = username;
        this.password = password;
        this.turmas = new HashSet<>();
        this.projeto = null;
        this.papelProjeto = null;
    }

    public Aluno(User user) {
        this.nome = user.getNome();
        this.matricula = user.getMatricula();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.turmas = new HashSet<>();
        this.projeto = null;
        this.papelProjeto = null;
    }

}