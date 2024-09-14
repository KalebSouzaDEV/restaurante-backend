package com.portfolioKaleb.restaurante.controller;

import com.portfolioKaleb.restaurante.entity.Categorie;
import com.portfolioKaleb.restaurante.service.CategoriesService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        List<Categorie> listaCategorias = categoriesService.getAllCategories();
        if (listaCategorias != null) {
            return new ResponseEntity<>(listaCategorias, HttpStatus.OK);
        }
        return new ResponseEntity<>("Erro ao listar categorias", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(@RequestBody Categorie categorie){
        boolean isCreated = categoriesService.createCategorie(categorie);
        if (isCreated) {
          return new ResponseEntity<String>("Categoria criada com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Falha ao criar categoria", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{categorieName}")
    public ResponseEntity<String> deleteCategorie(@PathVariable String categorieName) {
        boolean isDeleted = categoriesService.deleteCategorie(categorieName);
        if (isDeleted) {
            return new ResponseEntity<String>("Deletada com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Falha ao deletar categoria", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{categorieName}")
    public ResponseEntity<?> editCategorie(@RequestBody Categorie categorie, @PathVariable String categorieName) {
        Categorie newCategory = categoriesService.editCategorie(categorieName, categorie);
        if (newCategory != null) {
            return new ResponseEntity<Categorie>(newCategory, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Falha ao modificar categoria", HttpStatus.BAD_REQUEST);
    }
}
