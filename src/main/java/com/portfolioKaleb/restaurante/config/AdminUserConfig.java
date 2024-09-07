package com.portfolioKaleb.restaurante.config;

import com.portfolioKaleb.restaurante.entity.Role;
import com.portfolioKaleb.restaurante.entity.User;
import com.portfolioKaleb.restaurante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        var userAdmin = userRepository.findUserByName("admin");
        if (userAdmin != null) {
            System.out.println("Usuário já cadastrado");
        } else {
            Role role = Role.Values.getRoleByName("admin");
            User user = new User(UUID.randomUUID().toString(),"admin", passwordEncoder.encode("123456"), Set.of(role));
            userRepository.createUser(user, true);
        }
    }
}
