package com.portfolioKaleb.restaurante.controller;


import com.portfolioKaleb.restaurante.entity.Order;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createNewOrder(@RequestBody Order order){
        Response<String> retorno = orderService.createOrder(order);
        if (!retorno.hasMessage()) {
            return new ResponseEntity<>(retorno.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(retorno.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() throws Exception {
        Response<List<Order>> orders = orderService.getOrders();
        if (!orders.hasMessage()) {
            return new ResponseEntity<>(orders.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(orders.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{clientName}")
    public ResponseEntity<?> getAllOrdersByClient(@PathVariable String clientName) throws Exception {
        Response<List<Order>> orders = orderService.getOrdersByClient(clientName);
        if (!orders.hasMessage()) {
            return new ResponseEntity<>(orders.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(orders.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
