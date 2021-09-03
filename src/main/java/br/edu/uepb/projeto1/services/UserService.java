package br.edu.uepb.projeto1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.uepb.projeto1.domain.User;
import br.edu.uepb.projeto1.domain.Aluno;
import br.edu.uepb.projeto1.domain.Professor;
import br.edu.uepb.projeto1.repository.UserRepository;
import br.edu.uepb.projeto1.repository.AlunoRepository;
import br.edu.uepb.projeto1.repository.ProfessorRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUpUser(User user, String tipo) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(tipo.equals("aluno")) {
            Aluno aluno = new Aluno(user);
            alunoRepository.save(aluno);
        } else if(tipo.equals("professor")) {
            Professor professor = new Professor(user);
            professorRepository.save(professor);
        } else {
            userRepository.save(user);
        }

    }
}