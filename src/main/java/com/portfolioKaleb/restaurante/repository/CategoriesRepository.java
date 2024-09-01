package com.portfolioKaleb.restaurante.repository;

import com.portfolioKaleb.restaurante.database.Connect;
import com.portfolioKaleb.restaurante.entity.Categorie;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CategoriesRepository extends Connect {

    public List<Categorie> getAllCategories(){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM categories");
            result = stmt.executeQuery();

            List<Categorie> categories = new ArrayList<>();
            while (result.next()) {
                Categorie novaCategoria = new Categorie(result.getString("id"), result.getString("name"), result.getTimestamp("createdAt"));
                categories.add(novaCategoria);
            }
            stmt.close();
            closeConnection();
            return categories;
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
            return null;
        }
    }

    public Boolean createCategorie(Categorie categorie) {
        try {
            openConnection();
            stmt = connection.prepareStatement("INSERT INTO categories VALUES (?, ?, ?)");
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, categorie.getName());
            stmt.setTimestamp(3, Timestamp.from(Instant.now()));
            stmt.execute();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO22: " + e);
            return false;
        }
    }

    public Boolean deleteCategorie(Categorie categorie) {
        try {
            openConnection();
            stmt = connection.prepareStatement("DELETE FROM categories WHERE name = ?");
            stmt.setString(1, categorie.getName());
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO33: " + e);
            return false;
        }
    }

    public Categorie editCategorie(String categorieName, Categorie categorie){
        try {
            openConnection();
            stmt = connection.prepareStatement("UPDATE categories SET name = ? WHERE name = ?");
            stmt.setString(1, categorie.getName());
            stmt.setString(2, categorieName);
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("SELECT * FROM categories WHERE name = ?");
            stmt.setString(1, categorie.getName());
            result = stmt.executeQuery();

            Categorie newCategorie = null;
            if (result.next()) {
                newCategorie = new Categorie(result.getString("id"), result.getString("name"), result.getTimestamp("createdAt"));
            }
            stmt.close();
            closeConnection();
            return newCategorie;
        } catch (Exception e) {
            System.out.println("ERRO44: " + e);
            return null;
        }
    }
}
