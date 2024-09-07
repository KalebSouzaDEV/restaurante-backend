package com.portfolioKaleb.restaurante.controller;

import com.portfolioKaleb.restaurante.controller.dto.CreateUserDTO;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.entity.User;
import com.portfolioKaleb.restaurante.repository.UserRepository;
import com.portfolioKaleb.restaurante.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO user) {
        Response<String> response = userService.createUser(user);
        if (!response.hasMessage()) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/users")
    public ResponseEntity<?> listAllUsers() {
        Response<List<User>> response = userService.getAllUsers();
        if (!response.hasMessage()) {
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
