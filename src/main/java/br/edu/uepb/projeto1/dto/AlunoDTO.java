package br.edu.uepb.projeto1.dto;

import br.edu.uepb.projeto1.domain.PapelProjeto;

//import java.util.Set;

import lombok.Data;

@Data
public class AlunoDTO {
    
    private Long id;
    private String nome;
    private String matricula;
    private String email;
    private String username;
    private String password;
    //private Set<TurmaDTO> turmas;
    //private ProjetoDTO projeto;
    private PapelProjeto papelProjeto;

}