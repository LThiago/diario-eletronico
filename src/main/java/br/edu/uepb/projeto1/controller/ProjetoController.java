package br.edu.uepb.projeto1.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.dto.ProjetoDTO;
import br.edu.uepb.projeto1.mapper.ProjetoMapper;
import br.edu.uepb.projeto1.services.ProjetoService;

import br.edu.uepb.projeto1.dto.GenericResponseErrorDTO;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.exceptions.NaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/projetos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@Api(value = "Projeto1")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService; 

    @Autowired
    private ProjetoMapper projetoMapper;

    @GetMapping
    @ApiOperation(value = "Obtém uma lista de projetos")
    public List<ProjetoDTO> getProjetos() {
        List<Projeto> projetos = projetoService.listAllProjetos();
        return projetos.stream()
                        .map(projetoMapper::convertToProjetoDTO)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtém um projeto a partir do ID")
    public ResponseEntity<?> getProjetoById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(projetoMapper.convertToProjetoDTO(projetoService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PostMapping
    @ApiOperation(value = "Cria um projeto")
    public ResponseEntity<?> createProjeto(@RequestBody ProjetoDTO projetoDTO) {
        try {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            return new ResponseEntity<>(projetoService.createProjeto(projeto), HttpStatus.CREATED);
        } catch (ExistingSameNameException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @Transactional
    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um projeto")
    public ProjetoDTO updateProjeto(@PathVariable("id") Long id, @RequestBody ProjetoDTO projetoDTO) throws NaoEncontradoException {
        Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
        projetoService.updateProjeto(id, projeto);
        return projetoMapper.convertToProjetoDTO(projetoService.updateProjeto(id, projeto));
    }

    @Transactional
    @PutMapping("/{projetoId}/matricularAluno/{alunoId}")
    @ApiOperation(value = "Vincula um aluno em um projeto")
    public ProjetoDTO matricularAluno(@PathVariable("projetoId") Long projetoId, @PathVariable("alunoId") Long alunoId, @RequestBody ProjetoDTO projetoDTO) throws NaoEncontradoException {
        Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
        return projetoMapper.convertToProjetoDTO(projetoService.vinculaAluno(projetoId, alunoId, projeto));
    }

    @Transactional
    @PutMapping("/{projetoId}/vincularProfessor/{professorId}")
    @ApiOperation(value = "Vincula um aluno em um projeto")
    public ProjetoDTO vincularProfessor(@PathVariable("projetoId") Long projetoId, @PathVariable("professorId") Long professorId, @RequestBody ProjetoDTO projetoDTO) throws NaoEncontradoException {
        Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);        
        return projetoMapper.convertToProjetoDTO(projetoService.vinculaProfessor(projetoId, professorId, projeto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove um projeto")
    public void deleteProjeto(@PathVariable Long id) {
        projetoService.deleteProjeto(id);
    }

}