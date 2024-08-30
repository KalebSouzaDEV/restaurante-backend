package com.portfolioKaleb.restaurante.service;


import com.portfolioKaleb.restaurante.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Boolean createProduct() throws Exception {
        if (productsRepository.createProduct("Batata Pequena (300g)", 9.90, "frituras", "https://imgur.com")) {
            System.out.println("Veio aqui??");
            return true;
        }
        return false;
    }
}
