package com.portfolioKaleb.restaurante.repository;

import com.portfolioKaleb.restaurante.database.Connect;
import com.portfolioKaleb.restaurante.entity.Order;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository extends Connect {

    public Boolean createOrder(Order order){
        try {
            openConnection();
            stmt = connection.prepareStatement("INSERT INTO orders VALUES(?, ?, ?, ?, ?)");
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setDouble(2, order.getValueOrder());
            stmt.setString(3, order.getMethod());
            stmt.setString(4, order.getClient());
            stmt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmt.execute();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO 0004: " + e);
            return false;
        }
    }

    public List<Order> listAllOrders() throws Exception {
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM orders");
            result = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (result.next()) {
                orders.add(new Order(result.getString("id"), result.getDouble("valueorder"), result.getString("method"), result.getString("client"), result.getTimestamp("createdat")));
            }
            return orders;
        } catch (Exception e) {
            return null;
        } finally {
            stmt.close();
            closeConnection();
        }
    }

    public List<Order> listAllOrdersByClient(String clientName) throws Exception {
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM orders WHERE client = ?");
            stmt.setString(1, clientName);
            result = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (result.next()) {
                orders.add(new Order(result.getString("id"), result.getDouble("valueorder"), result.getString("method"), result.getString("client"), result.getTimestamp("createdat")));
            }
            return orders;
        } catch (Exception e) {
            return null;
        } finally {
            stmt.close();
            closeConnection();
        }
    }


}
