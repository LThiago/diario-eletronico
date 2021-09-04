package br.edu.uepb.projeto1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.PapelProjeto;
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

    public Projeto createProjeto(Projeto projeto, Authentication authentication) throws ExistingSameNameException {
        if(!professorRepository.findByUsername(authentication.getName()).isPresent()) {
            throw new ExistingSameNameException("Apenas professores podem criar projetos!");
        } else if (projetoRepository.findByNome(projeto.getNome()).isPresent()) {
            throw new ExistingSameNameException("Já existe um projeto com esse nome!");
        }
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

    public Projeto vinculaAluno(Long projetoId, Long alunoId, String papel, Authentication authentication) throws Exception {
        Professor professorRequest = professorRepository.findByUsername(authentication.getName()).get();
        Projeto projetoProfessorRequest = professorRequest.getProjeto();

        if(projetoProfessorRequest == null) {
            throw new Exception("O professor não está vinculado a um projeto.");
        } else if(professorRequest.getProjeto().getId() != projetoId) {
            throw new Exception("Apenas o coordenador do projeto pode vincular alunos!");
        }
        
        return projetoRepository.findById(projetoId).map(projeto -> {
            Aluno aluno = alunoRepository.getById(alunoId);
            projeto.getAlunos().add(aluno);
            aluno.setProjeto(projeto);
            aluno.setPapelProjeto(PapelProjeto.valueOf(papel.toUpperCase()));
            alunoRepository.save(aluno);
            return projetoRepository.save(projeto);
        }).orElseThrow(() -> new NaoEncontradoException("Projeto não encontrado."));
    }

}