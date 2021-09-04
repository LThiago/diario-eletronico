package br.edu.uepb.projeto1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.PapelProjeto;
import br.edu.uepb.projeto1.domain.Turma;
import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.domain.Projeto;
import br.edu.uepb.projeto1.repository.AlunoRepository;
import br.edu.uepb.projeto1.repository.TurmaRepository;
import br.edu.uepb.projeto1.repository.ProfessorRepository;
import br.edu.uepb.projeto1.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Projeto1Application implements CommandLineRunner {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Projeto1Application.class, args);
	}
    // http://localhost:8080/
    // http://localhost:8080/h2-console
    // http://localhost:8080/swagger-ui.html

    @Override
    public void run(String... args) throws Exception {

        turmaRepository.deleteAllInBatch();
        professorRepository.deleteAllInBatch();
        alunoRepository.deleteAllInBatch();
        projetoRepository.deleteAllInBatch();

        Turma turma1 = new Turma("WEB", "201", "1234567");
		Professor professor1 = new Professor("Ramon", "Computação", "123456781", "ramon@gmail.com", "ramon", "1234");
        Projeto projeto1 = new Projeto("Spring Boot", "Curso básico de Spring Boot", professor1);
        professor1.getTurmas().add(turma1);
        turma1.getProfessores().add(professor1);
        professor1.setProjeto(projeto1);
        professor1.setPapelProjeto(PapelProjeto.COORDENADOR);

        Aluno aluno1 = new Aluno("Gustavo Silva", "123456782", "ghustavosm@gmail.com", "gustavo", "1234");
        aluno1.getTurmas().add(turma1);
        turma1.getAlunos().add(aluno1);
        aluno1.setProjeto(projeto1);
        projeto1.getAlunos().add(aluno1);
        aluno1.setPapelProjeto(PapelProjeto.ESTAGIO);
        
        Aluno aluno2 = new Aluno("Lucas Gabriel", "123456783", "lucasgabriel@gmail.com", "lucas", "1234");
        aluno2.getTurmas().add(turma1);
        turma1.getAlunos().add(aluno2);
        aluno2.setProjeto(projeto1);        
        projeto1.getAlunos().add(aluno2);
        aluno2.setPapelProjeto(PapelProjeto.JUNIOR);
        
        Aluno aluno3 = new Aluno("Tiago Silva", "123456784", "tiagosilva@gmail.com", "tiago", "1234");

        turmaRepository.save(turma1);
        projetoRepository.save(projeto1);
        professorRepository.save(professor1);
        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
        alunoRepository.save(aluno3);
        
    }
	
}