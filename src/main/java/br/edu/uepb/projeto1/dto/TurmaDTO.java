package br.edu.uepb.projeto1.dto;

//import java.util.Set;

import lombok.Data;

@Data
public class TurmaDTO {
    
    private Long id;
    private String nome;
    private String sala;
    private String codigo;
    /*private Set<AlunoDTO> alunos;
    private Set<ProfessorDTO> professores;*/

}
