package com.portfolioKaleb.restaurante.controller;

import com.portfolioKaleb.restaurante.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping
    public ResponseEntity<String> createProduct() throws Exception {
        Boolean retorno = this.productsService.createProduct();
        if (retorno) {
            return new ResponseEntity<String>("Produto criado com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Produto não foi criado", HttpStatus.BAD_REQUEST);
    }
}
