package br.edu.uepb.projeto1.settings;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.uepb.projeto1.mapper.UserMapper;
import br.edu.uepb.projeto1.mapper.AlunoMapper;
import br.edu.uepb.projeto1.mapper.ProfessorMapper;
import br.edu.uepb.projeto1.mapper.TurmaMapper;
import br.edu.uepb.projeto1.mapper.ProjetoMapper;

@Configuration
public class Projeto1MapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
        return modelMapper;
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public AlunoMapper alunoMapper() {
        return new AlunoMapper();
    }

    @Bean
    public ProfessorMapper professorMapper() {
        return new ProfessorMapper();
    }

    @Bean
    public TurmaMapper turmaMapper() {
        return new TurmaMapper();
    }

    @Bean
    public ProjetoMapper projetoMapper() {
        return new ProjetoMapper();
    }

}