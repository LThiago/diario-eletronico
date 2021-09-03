package br.edu.uepb.projeto1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uepb.projeto1.domain.Turma;
import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.exceptions.ExistingSameNameException;
import br.edu.uepb.projeto1.exceptions.NaoEncontradoException;
import br.edu.uepb.projeto1.repository.TurmaRepository;
import br.edu.uepb.projeto1.repository.AlunoRepository;
import br.edu.uepb.projeto1.repository.ProfessorRepository;
import javassist.NotFoundException;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public Turma createTurma(Turma turma) throws ExistingSameNameException {
        if (turmaRepository.findByNome(turma.getNome()).isPresent())
            throw new ExistingSameNameException("Já existe uma turma com esse nome!");
        return turmaRepository.save(turma);
    }

    public Turma updateTurma(Long id, Turma turmaRequest) throws NaoEncontradoException {
        return turmaRepository.findById(id).map(turma -> {
            turma.setNome(turmaRequest.getNome() == null ? turma.getNome() : turmaRequest.getNome());
            turma.setSala(turmaRequest.getSala() == null ? turma.getSala() : turmaRequest.getSala());
            turma.setCodigo(turmaRequest.getCodigo() == null ? turma.getCodigo() : turmaRequest.getCodigo());
            return turmaRepository.save(turma);
        }).orElseThrow(() -> new NaoEncontradoException("Turma não encontrada."));
    }

    public List<Turma> listAllTurmas() {
        return turmaRepository.findAll();
    }

    public Turma findById(Long id) throws NotFoundException {
        return turmaRepository.findById(id).orElseThrow(() -> new NotFoundException("Não existe uma turma com esse identificador!"));
    }

    public void deleteTurma(Long id) {
        Turma turmaToDelete = turmaRepository.findById(id).get();
        turmaRepository.delete(turmaToDelete);
    }

    public Turma matriculaAluno(Long turmaId, Long alunoId, Turma turmaRequest) throws NaoEncontradoException {
        return turmaRepository.findById(turmaId).map(turma -> {
            Aluno aluno = alunoRepository.getById(alunoId);
            turma.getAlunos().add(aluno);
            aluno.getTurmas().add(turma);
            alunoRepository.save(aluno);
            return turmaRepository.save(turma);
        }).orElseThrow(() -> new NaoEncontradoException("Turma não encontrada."));
    }

    public Turma vinculaProfessor(Long turmaId, Long professorId, Turma turmaRequest) throws NaoEncontradoException {
        return turmaRepository.findById(turmaId).map(turma -> {
            Professor professor = professorRepository.getById(professorId);
            turma.getProfessores().add(professor);
            professor.getTurmas().add(turma);
            professorRepository.save(professor);
            return turmaRepository.save(turma);
        }).orElseThrow(() -> new NaoEncontradoException("Turma não encontrada."));
    }
}