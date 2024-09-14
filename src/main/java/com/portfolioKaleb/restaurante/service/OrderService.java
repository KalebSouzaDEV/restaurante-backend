package com.portfolioKaleb.restaurante.service;

import com.portfolioKaleb.restaurante.entity.Order;
import com.portfolioKaleb.restaurante.entity.Response;
import com.portfolioKaleb.restaurante.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Response<String> createOrder(Order order){
        boolean orderInserted = orderRepository.createOrder(order);
        System.out.println("VEM AQUI NAO ??: " + orderInserted);
        if (orderInserted){
            return new Response<>("Pedido criado com sucesso", null);
        }
        return new Response<>(null, "Falha ao criar pedido");
    }

    public Response<List<Order>> getOrders() throws Exception {
        List<Order> orders = orderRepository.listAllOrders();
        if (orders != null) {
            return new Response<>(orders, null);
        }
        return new Response<>(null, "Falha ao buscar pedidos");
    }

    public Response<List<Order>> getOrdersByClient(String username) throws Exception {
        List<Order> orders = orderRepository.listAllOrdersByClient(username);
        if (orders != null) {
            return new Response<>(orders, null);
        }
        return new Response<>(null, "Falha ao buscar pedidos");
    }


}
