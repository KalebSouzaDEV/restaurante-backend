package com.portfolioKaleb.restaurante.repository;

import com.portfolioKaleb.restaurante.controller.dto.LoginRequest;
import com.portfolioKaleb.restaurante.database.Connect;
import com.portfolioKaleb.restaurante.entity.Role;
import com.portfolioKaleb.restaurante.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepository extends Connect {

    public User logInUser(String username, LoginRequest loginRequest, BCryptPasswordEncoder bCryptPasswordEncoder){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            stmt.setString(1, username);
             result = stmt.executeQuery();
            Set<Role> roles = new HashSet<>();

            if (result.next()) {
                Role role = Role.Values.getRoleByName(result.getString("role"));
                roles.add(role);
                User user = new User(result.getString("id"), result.getString("login"), result.getString("password"), roles);
                if (user.isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
                    return user;
                } else {
                    System.out.println("Usuário ou senha incorreta");
                    throw new BadCredentialsException("Usuário ou senha incorreta");
                }
            } else {
                System.out.println("nenhuma conta encontrada");
                throw new BadCredentialsException("Usuário ou senha incorreta");
            }
        } catch (Exception e) {
            System.out.println("Erro 0002: " + e);
            return null;
        }
    }

    public User findUserByName(String username){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            stmt.setString(1, username);
            result = stmt.executeQuery();
            Set<Role> roles = new HashSet<>();

            if (result.next()) {
                Role role = Role.Values.getRoleByName(result.getString("role"));
                roles.add(role);
                User user = new User(result.getString("id"), result.getString("login"), result.getString("password"), roles);
                return user;
            } else {
                throw new BadCredentialsException("Usuário ou senha incorreta");
            }
        } catch (Exception e) {
            System.out.println("Erro 0001: " + e);
            return null;
        }
    }


    public Boolean createUser(User user, boolean isAdmin) {
        try {
            stmt = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?)");
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, isAdmin ? "admin" : "client");
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> getAllUsers(){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM users");
            result = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                Role role = Role.Values.getRoleByName(result.getString("role"));

                users.add(new User(result.getString("id"), result.getString("login"), result.getString("password"), Set.of(role)));
            }
            return users;
        } catch (Exception e) {
            System.out.println("ERRO 0003: " + e);
            return null;
        }
    }

}
