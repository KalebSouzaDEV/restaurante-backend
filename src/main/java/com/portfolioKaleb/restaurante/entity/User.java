package com.portfolioKaleb.restaurante.entity;

import com.portfolioKaleb.restaurante.controller.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String id;
    private String login;
    private String password;
    private Set<Role> roles;

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
