package com.portfolioKaleb.restaurante.controller;

import com.portfolioKaleb.restaurante.entity.Product;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) throws Exception {
        Response<Boolean> retorno = this.productsService.createProduct(product);
        if (!retorno.hasMessage()) {
            return new ResponseEntity<String>("Produto criado com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<String>(retorno.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> produtos = productsService.getAllProducts();
        if (produtos != null) {
            return new ResponseEntity<List<Product>>(produtos, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Produtos não retornados", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{productID}")
    public ResponseEntity<?> getProductByID(@PathVariable String productID){
        Product product = productsService.getProductByID(productID);
        if (product != null) {
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Produto não foi encontrado", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categorie/{categorieName}")
    public ResponseEntity<?> getProductsByCategorie(@PathVariable String categorieName) {
        Response<List<Product>> produtos = productsService.getProductsByCategorie(categorieName);
        if (!produtos.hasMessage()) {
            return new ResponseEntity<>(produtos.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(produtos.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{productID}")
    public ResponseEntity<?> editProduct(@RequestBody Product product, @PathVariable String productID){
        product.setId(productID);
        Response<Product> newProduct = productsService.editProduct(product);
        if (!newProduct.hasMessage()) {
            return new ResponseEntity<Product>(newProduct.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(newProduct.getMessage(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productID) {
        Boolean productIsDeleted = productsService.deleteProduct(productID);
        if (productIsDeleted) {
            return new ResponseEntity<String>("Produto deletado com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Produto não foi deletado", HttpStatus.BAD_REQUEST);
    }


}
