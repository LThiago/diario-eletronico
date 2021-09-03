package br.edu.uepb.projeto1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.dto.ProjetoDTO;

public class ProjetoMapper {

    @Autowired
    private ModelMapper modelMapper;
    
    public ProjetoDTO convertToProjetoDTO(Projeto projeto) {
        ProjetoDTO projetoDTO = modelMapper.map(projeto, ProjetoDTO.class);
        return projetoDTO;
    }

    public Projeto convertFromProjetoDTO(ProjetoDTO projetoDTO) {
        Projeto projeto = modelMapper.map(projetoDTO, Projeto.class);    
        return projeto;
    }

}