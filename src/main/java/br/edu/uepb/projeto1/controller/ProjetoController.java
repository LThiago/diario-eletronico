package br.edu.uepb.projeto1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.dto.ProjetoDTO;
import br.edu.uepb.projeto1.mapper.AlunoMapper;
import br.edu.uepb.projeto1.mapper.ProfessorMapper;
import br.edu.uepb.projeto1.mapper.ProjetoMapper;
import br.edu.uepb.projeto1.services.ProjetoService;
import br.edu.uepb.projeto1.dto.AlunoDTO;
import br.edu.uepb.projeto1.dto.GenericResponseErrorDTO;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.exceptions.NaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private AlunoMapper alunoMapper;

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

    @Transactional
    @GetMapping("/{id}/alunos")
    @ApiOperation(value = "Obtém os alunos de um projeto a partir do ID")
    public List<AlunoDTO> getAlunosProjeto(@PathVariable Long id) throws NotFoundException {
        List<Aluno> alunos = new ArrayList<>(projetoService.findById(id).getAlunos());
        return alunos.stream()
                        .map(alunoMapper::convertToAlunoDTO)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}/coordenador")
    @ApiOperation(value = "Obtém o coordenador de um projeto a partir do ID")
    public ResponseEntity<?> getCoordenadorProjeto(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(professorMapper.convertToProfessorDTO(projetoService.findById(id).getCoordenador()), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PostMapping
    @ApiOperation(value = "Cria um projeto")
    public ResponseEntity<?> createProjeto(Authentication authentication, @RequestBody ProjetoDTO projetoDTO) {
        try {
            Projeto projeto = projetoMapper.convertFromProjetoDTO(projetoDTO);
            return new ResponseEntity<>(projetoService.createProjeto(projeto, authentication), HttpStatus.CREATED);
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
    @PutMapping("/{projetoId}/vincularAluno/{alunoId}/{papel}")
    @ApiOperation(value = "Vincula um aluno em um projeto")
    public ProjetoDTO matricularAluno(Authentication authentication, @PathVariable("projetoId") Long projetoId, @PathVariable("alunoId") Long alunoId, @PathVariable("papel") String papel) throws Exception {
        return projetoMapper.convertToProjetoDTO(projetoService.vinculaAluno(projetoId, alunoId, papel, authentication));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove um projeto vazio")
    public void deleteProjeto(@PathVariable Long id) {
        projetoService.deleteProjeto(id);
    }

}