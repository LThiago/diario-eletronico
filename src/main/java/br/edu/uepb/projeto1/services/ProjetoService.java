package br.edu.uepb.projeto1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.exceptions.NaoEncontradoException;
import br.edu.uepb.projeto1.repository.ProjetoRepository;
import br.edu.uepb.projeto1.repository.AlunoRepository;
import br.edu.uepb.projeto1.repository.ProfessorRepository;
import javassist.NotFoundException;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public Projeto createProjeto(Projeto projeto) throws ExistingSameNameException {
        if (projetoRepository.findByNome(projeto.getNome()).isPresent())
            throw new ExistingSameNameException("Já existe um projeto com esse nome!");
        return projetoRepository.save(projeto);
    }

    public Projeto updateProjeto(Long id, Projeto projeto) throws NaoEncontradoException {
        projeto.setId(id);
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listAllProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto findById(Long id) throws NotFoundException {
        return projetoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não existe um projeto com esse identificador!"));
    }

    public void deleteProjeto(Long id) {
        Projeto projetoToDelete = projetoRepository.findById(id).get();
        projetoRepository.delete(projetoToDelete);
    }

    public Projeto vinculaAluno(Long projetoId, Long alunoId, Projeto projetoRequest) throws NaoEncontradoException {
        return projetoRepository.findById(projetoId).map(projeto -> {
            Aluno aluno = alunoRepository.getById(alunoId);
            projeto.getAlunos().add(aluno);
            aluno.setProjeto(projeto);
            alunoRepository.save(aluno);
            return projetoRepository.save(projeto);
        }).orElseThrow(() -> new NaoEncontradoException("Projeto não encontrado."));
    }

    public Projeto vinculaProfessor(Long projetoId, Long professorId, Projeto projetoRequest) throws NaoEncontradoException {
        return projetoRepository.findById(projetoId).map(projeto -> {
            Professor professor = professorRepository.getById(professorId);
            projeto.setProfessor(professor);
            professor.setProjeto(projeto);
            professorRepository.save(professor);
            return projetoRepository.save(projeto);
        }).orElseThrow(() -> new NaoEncontradoException("Projeto não encontrado."));
    }
}