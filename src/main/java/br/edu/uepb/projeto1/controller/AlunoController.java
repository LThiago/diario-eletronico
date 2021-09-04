package br.edu.uepb.projeto1.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.dto.AlunoDTO;
import br.edu.uepb.projeto1.dto.GenericResponseErrorDTO;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.mapper.AlunoMapper;
import br.edu.uepb.projeto1.services.AlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/alunos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@Api(value = "Projeto1")
public class AlunoController {

    @Autowired
    private AlunoService alunoService; 

    @Autowired
    private AlunoMapper alunoMapper;

    @GetMapping
    @ApiOperation(value = "Obtém uma lista de alunos")
    public List<AlunoDTO> getAlunos() {
        List<Aluno> alunos = alunoService.listAllAlunos();
        return alunos.stream()
                        .map(alunoMapper::convertToAlunoDTO)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtém um aluno a partir do ID")
    public ResponseEntity<?> getAlunoById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(alunoMapper.convertToAlunoDTO(alunoService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PostMapping
    @ApiOperation(value = "Cria um aluno")
    public ResponseEntity<?> createAluno(@RequestBody AlunoDTO alunoDTO) {
        try {
            Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
            return new ResponseEntity<>(alunoService.createAluno(aluno), HttpStatus.CREATED);
        } catch (ExistingSameNameException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um aluno")
    public AlunoDTO updateAluno(@PathVariable("id") Long id, @RequestBody AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
        return alunoMapper.convertToAlunoDTO(alunoService.updateAluno(id, aluno));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove um aluno")
    public void deleteAluno(@PathVariable Long id) {
        alunoService.deleteAluno(id);
    }

}