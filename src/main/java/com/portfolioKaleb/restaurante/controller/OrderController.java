package com.portfolioKaleb.restaurante.controller;


import com.portfolioKaleb.restaurante.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    @PostMapping
    public ResponseEntity<String> createNewOrder(@RequestBody Order order){

    }
}
