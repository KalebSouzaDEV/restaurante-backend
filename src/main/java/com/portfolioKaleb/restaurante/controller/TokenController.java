package com.portfolioKaleb.restaurante.controller;

import com.portfolioKaleb.restaurante.controller.dto.LoginRequest;
import com.portfolioKaleb.restaurante.controller.dto.LoginResponse;
import com.portfolioKaleb.restaurante.entity.Role;
import com.portfolioKaleb.restaurante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {
    @Autowired
    private UserRepository userRepository;

    private final JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var user = userRepository.logInUser(loginRequest.login(), loginRequest, this.bCryptPasswordEncoder);
        System.out.println("Veio user: " + user);
        if (user != null) {
            var expiresIn = 300L; // Tempo que o token vai expirar
            var now = Instant.now();
            var scopes = user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(" "));

            var claims = JwtClaimsSet.builder()
                    .issuer("backend")
                    .subject(user.getId())
                    .expiresAt(now.plusSeconds(expiresIn))
                    .issuedAt(now)
                    .claim("scope", scopes)
                    .claim("name", user.getNome())
                    .build();

            String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return new ResponseEntity<>(new LoginResponse(jwtValue, expiresIn, user.getRoles()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Erro ao se logar", HttpStatus.BAD_REQUEST);
    }
}
