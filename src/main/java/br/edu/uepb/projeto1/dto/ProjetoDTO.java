package br.edu.uepb.projeto1.dto;

//import java.util.Set;

import lombok.Data;

@Data
public class ProjetoDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private ProfessorDTO coordenador;
    //private Set<AlunoDTO> alunos;

}
