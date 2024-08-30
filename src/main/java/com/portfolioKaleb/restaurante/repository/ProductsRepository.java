package com.portfolioKaleb.restaurante.repository;
import com.portfolioKaleb.restaurante.database.Connect;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProductsRepository extends Connect {
    public boolean createProduct(String name, double price, String categorie, String imageLink) throws Exception {
        try {
            openConnection();
            stmt = connection.prepareStatement("insert into products VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, String.valueOf(UUID.randomUUID()));
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setString(4, categorie);
            stmt.setString(5, imageLink);
            stmt.execute();
            stmt.close();
            closeConnection();
            System.out.println("Inserio balado");
            return true;
        } catch (Exception e) {
            System.out.println("Deu erro nessa porraaaaa: " + e);
            return false;
        }
    }
}
