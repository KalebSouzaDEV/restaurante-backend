package com.portfolioKaleb.restaurante.service;


import com.portfolioKaleb.restaurante.entity.Product;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.repository.CategoriesRepository;
import com.portfolioKaleb.restaurante.repository.ProductsRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;


    public Response<Boolean> createProduct(Product product) throws Exception {
        if (categoriesRepository.getCategorieByName(product.getCategorie()) != null) {
            if (productsRepository.createProduct(product.getName(), product.getPrice(), product.getCategorie(), product.getImage())) {
                return new Response<>(true);
            }
        } else  {
            return new Response<>("Categoria não encontrada");
        }
        return new Response<>("Falha ao criar produto.");
    }

    public Product getProductByID(String productID){
        Product retornoProduto = productsRepository.getProductByID(productID);
        if (retornoProduto != null) {
            return retornoProduto;
        }
        return null;
    }

    public Response<List<Product>> getProductsByCategorie(String categorieName) {
        if (categoriesRepository.getCategorieByName(categorieName) != null) {
            List<Product> productsList = productsRepository.getProductsByCategorie(categorieName);
            if (productsList != null) {
                return new Response<>(productsList);
            }
        } else {
            return new Response<>("Categoria não existente");
        }
        return new Response<>("Falha ao buscar produtos");
    }

    public List<Product> getAllProducts(){
        List<Product> productsList = productsRepository.getAllProducts();
        if (productsList != null) {
            return productsList;
        }
        return null;
    }

    public Response<Product> editProduct(Product product){

        Map<String, Object> updatesList = new HashMap<>();
        updatesList.put("id", product.getId());
        if (product.getName() != null) {
            updatesList.put("name", product.getName());
        }
        if (product.getPrice() != 0.0) {
            updatesList.put("price", product.getPrice());
        }
        if (product.getCategorie() != null) {
            if (categoriesRepository.getCategorieByName(product.getCategorie()) != null) {
                updatesList.put("categorie", product.getCategorie());
            } else {
                return new Response<>("Categoria não encontrada...");
            }
        }
        if (product.getImage() != null) {
            updatesList.put("image", product.getImage());
        }

        Product newProduct = productsRepository.editProduct(updatesList);
        if (newProduct != null){
            return new Response<>(newProduct);
        }
        return null;
    }

    public Boolean deleteProduct(String productID){
        Boolean isDeleted = productsRepository.deleteProduct(productID);
        return isDeleted;
    }

}
