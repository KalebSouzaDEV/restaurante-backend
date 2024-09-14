package com.portfolioKaleb.restaurante.service;

import com.portfolioKaleb.restaurante.controller.dto.CreateUserDTO;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.entity.Role;
import com.portfolioKaleb.restaurante.entity.User;
import com.portfolioKaleb.restaurante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Response<String> createUser(CreateUserDTO user){
        if (userRepository.findUserByName(user.login()) != null) {
            return new Response<>("Usuário não disponível.", null);
        }

        Role role = Role.Values.getRoleByName("client");

        User newUser = new User(UUID.randomUUID().toString(), user.login(), passwordEncoder.encode(user.password()), user.nome(), user.phone(), user.address(), Set.of(role));
        userRepository.createUser(newUser, false);
        return new Response<>("Usuário criado com sucesso", null);
    }

    public Response<List<User>> getAllUsers(){
        List<User> users = userRepository.getAllUsers();
        if (users != null) {
            return new Response<>(users, null);
        }
        return new Response<>(null, "Falha ao retornar usuários");
    }
}
