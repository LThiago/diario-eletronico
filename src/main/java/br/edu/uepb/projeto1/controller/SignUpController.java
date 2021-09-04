package br.edu.uepb.projeto1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uepb.projeto1.dto.UserDTO;
import br.edu.uepb.projeto1.mapper.UserMapper;
import br.edu.uepb.projeto1.services.UserService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@Api(value = "Sign Up")
public class SignUpController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signup/aluno")
    public void signUpAluno(@RequestBody UserDTO userDTO) {
        userService.signUpUser(userMapper.convertFromUserDTO(userDTO), "aluno");
    }

    @PostMapping("/signup/professor")
    public void signUpProfessor(@RequestBody UserDTO userDTO) {
        userService.signUpUser(userMapper.convertFromUserDTO(userDTO), "professor");
    }
    
}