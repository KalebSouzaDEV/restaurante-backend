package com.portfolioKaleb.restaurante.service;


import com.portfolioKaleb.restaurante.entity.Categorie;
import com.portfolioKaleb.restaurante.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categorie> getAllCategories(){
        return categoriesRepository.getAllCategories();
    }

    public Boolean createCategorie(Categorie categorie){
        return categoriesRepository.createCategorie(categorie);
    }

    public Boolean deleteCategorie(Categorie categorie) {
        return categoriesRepository.deleteCategorie(categorie);
    }

    public Categorie editCategorie(String categorieName, Categorie categorie) {
        return categoriesRepository.editCategorie(categorieName, categorie);
    }
}
