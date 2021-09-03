package br.edu.uepb.projeto1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.Turma;
import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.repository.AlunoRepository;
import br.edu.uepb.projeto1.repository.TurmaRepository;
import br.edu.uepb.projeto1.repository.ProfessorRepository;
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

        Turma turma = new Turma("WEB", "201", "1234567");

		Professor professor = new Professor("Ramon", "Computação", "123456781", "ramon@gmail.com", "ramon", "1234");

        Aluno aluno1 = new Aluno("Gustavo Silva", "123456782", "ghustavosm@gmail.com", "gustavo", "1234");
        Aluno aluno2 = new Aluno("Lucas Gabriel", "123456783", "lucasgabriel@gmail.com", "lucas", "1234");
        Aluno aluno3 = new Aluno("Tiago Silva", "123456784", "tiagosilva@gmail.com", "tiago", "1234");

		turma.getProfessores().add(professor);
		professor.getTurmas().add(turma);

        turma.getAlunos().add(aluno1);
        aluno1.getTurmas().add(turma);

        turma.getAlunos().add(aluno2);        
        aluno2.getTurmas().add(turma);

        turmaRepository.save(turma);
        //alunoRepository.save(aluno1);
        //alunoRepository.save(aluno2);
        alunoRepository.save(aluno3);
        //professorRepository.save(professor);

    }
	
}