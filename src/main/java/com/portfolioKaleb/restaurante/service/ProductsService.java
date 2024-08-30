package com.portfolioKaleb.restaurante.service;


import com.portfolioKaleb.restaurante.entity.Product;
import com.portfolioKaleb.restaurante.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Boolean createProduct(Product product) throws Exception {
        if (productsRepository.createProduct(product.getName(), product.getPrice(), product.getCategorie(), product.getImage())) {
            return true;
        }
        return false;
    }

    public Product getProductByID(String productID){
        Product retornoProduto = productsRepository.getProductByID(productID);
        if (retornoProduto != null) {
            return retornoProduto;
        }
        return null;
    }
    public List<Product> getAllProducts(){
        List<Product> productsList = productsRepository.getAllProducts();
        if (productsList != null) {
            System.out.println(String.valueOf(productsList) +  " UAII");
            return productsList;
        }
        return null;
    }

    public Product editProduct(Product product){


        Map<String, Object> updatesList = new HashMap<>();
        updatesList.put("id", product.getId());
        if (product.getName() != null) {
            updatesList.put("name", product.getName());
        }
        if (product.getPrice() != 0.0) {
            updatesList.put("price", product.getPrice());
        }
        if (product.getCategorie() != null) {
            updatesList.put("categorie", product.getCategorie());
        }
        if (product.getImage() != null) {
            updatesList.put("image", product.getImage());
        }

        Product newProduct = productsRepository.editProduct(updatesList);
        if (newProduct != null){
            return newProduct;
        }
        return null;
    }

    public Boolean deleteProduct(String productID){
        Boolean isDeleted = productsRepository.deleteProduct(productID);
        return isDeleted;
    }

}
