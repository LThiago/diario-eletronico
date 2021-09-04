package br.edu.uepb.projeto1.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.dto.ProfessorDTO;
import br.edu.uepb.projeto1.dto.GenericResponseErrorDTO;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.mapper.ProfessorMapper;
import br.edu.uepb.projeto1.services.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/professores", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@Api(value = "Projeto1")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService; 

    @Autowired
    private ProfessorMapper professorMapper;

    @GetMapping
    @ApiOperation(value = "Obtém uma lista de professores")
    public List<ProfessorDTO> getProfessors() {
        List<Professor> professores = professorService.listAllProfessors();
        return professores.stream()
                        .map(professorMapper::convertToProfessorDTO)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtém um professor a partir do ID")
    public ResponseEntity<?> getProfessorById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(professorMapper.convertToProfessorDTO(professorService.findById(id)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PostMapping
    @ApiOperation(value = "Cria um professor")
    public ResponseEntity<?> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
            return new ResponseEntity<>(professorService.createProfessor(professor), HttpStatus.CREATED);
        } catch (ExistingSameNameException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um professor")
    public ProfessorDTO updateProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDTO professorDTO) {
        Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
        return professorMapper.convertToProfessorDTO(professorService.updateProfessor(id, professor));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove um professor")
    public void deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
    }
    
}