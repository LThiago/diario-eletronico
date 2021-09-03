package br.edu.uepb.projeto1.dto;

//import java.util.Set;

import lombok.Data;

@Data
public class ProfessorDTO {
    
    private Long id;
    private String nome;
    private String formacao;
    private String matricula;
    private String email;
    private String username;
    private String password;
    //private Set<TurmaDTO> turmas;

}
