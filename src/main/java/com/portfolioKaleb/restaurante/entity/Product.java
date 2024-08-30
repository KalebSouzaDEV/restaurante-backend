package com.portfolioKaleb.restaurante.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private double price;
    private String categorie;
    private String image;

    @Override
    public String toString(){
        return "Poduct { id=" + id + " | name=" + name + " | price=" + price + " | categorie=" + categorie + "}";
    }
}
