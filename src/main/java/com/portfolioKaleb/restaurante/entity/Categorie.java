package com.portfolioKaleb.restaurante.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Categorie {
    private String id;
    private String name;
    private Timestamp createdAt;
}
