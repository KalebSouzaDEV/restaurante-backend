package com.portfolioKaleb.restaurante.controller.dto;

import com.portfolioKaleb.restaurante.entity.Role;

import java.util.Set;

public record LoginResponse(String acessToken, Long expiresIn, Set<Role> roles) {
}
