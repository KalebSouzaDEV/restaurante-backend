package com.portfolioKaleb.restaurante.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private int price;
    private String categorie;
    private String image;
}
