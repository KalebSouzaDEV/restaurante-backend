package com.portfolioKaleb.restaurante.repository;
import com.portfolioKaleb.restaurante.database.Connect;
import com.portfolioKaleb.restaurante.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductsRepository extends Connect {
    public boolean createProduct(String name, double price, String categorie, String imageLink) throws Exception {
        try {
            openConnection();
            stmt = connection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, String.valueOf(UUID.randomUUID()));
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setString(4, categorie);
            stmt.setString(5, imageLink);
            stmt.execute();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI: " + e);
            return false;
        }
    }

    public Product getProductByID(String productID){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
            stmt.setString(1, productID);
            result = stmt.executeQuery();
            if (result.next()){
                Product produto = new Product();
                produto.setId(result.getString("id"));
                produto.setName(result.getString("name"));
                produto.setCategorie(result.getString("categorie"));
                produto.setPrice(result.getDouble("price"));
                produto.setImage(result.getString("image"));
                stmt.close();
                closeConnection();
                return produto;
            }
            stmt.close();
            closeConnection();
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI2: " + e);
            return null;
        }
        return null;
    }

    public List<Product> getProductsByCategorie(String categorieName) {
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM products WHERE categorie = ?");
            stmt.setString(1, categorieName);
            System.out.println("CATEGORIA NOME: " + categorieName);
            result = stmt.executeQuery();
            if (result.next()) {
                List<Product> produtos = new ArrayList<>();
                Product produto = new Product(result.getString("id"), result.getString("name"), result.getDouble("price"), result.getString("categorie"), result.getString("image"));
                produtos.add(produto);
                while (result.next()) {
                    System.out.println("WTTF: " + result.getString("name"));
                    produto = new Product(result.getString("id"), result.getString("name"), result.getDouble("price"), result.getString("categorie"), result.getString("image"));
                    produtos.add(produto);
                }
                stmt.close();
                closeConnection();
                return produtos;
            }
            stmt.close();
            closeConnection();
            return null;
        } catch (Exception e) {
            System.out.println("ERROO: " + e);
            return null;
        }
    }

    public List<Product> getAllProducts(){
        try {
            openConnection();
            stmt = connection.prepareStatement("SELECT * FROM products");
            result = stmt.executeQuery();

            List<Product> produtos = new ArrayList<>();
            while (result.next()) {
                System.out.println("Adicionado mais um produto");
                Product produto = new Product();
                produto.setId(result.getString("id"));
                produto.setName(result.getString("name"));
                produto.setCategorie(result.getString("categorie"));
                produto.setPrice(result.getDouble("price"));
                produto.setImage(result.getString("image"));
                produtos.add(produto);
            }
            stmt.close();
            closeConnection();
            return produtos;
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI2: " + e);
            return null;
        }
    }

    public Product editProduct(Map<String, Object> updates){
        if (!updates.containsKey("id")) {
            throw new IllegalArgumentException("ID is required to update the product");
        }
        try {
            StringBuilder sqlCommand = new StringBuilder("UPDATE products SET ");
            for (String key: updates.keySet()) {
                if (!key.equals("id")) {
                    sqlCommand.append(key).append(" = ?, ");
                }
            }
            sqlCommand.setLength(sqlCommand.length() - 2);
            sqlCommand.append(" WHERE id = ?");

            openConnection();
            stmt = connection.prepareStatement(sqlCommand.toString());
            int indexKey = 1;
            for (String key: updates.keySet()) {
                if (!key.equals("id")) {
                    stmt.setObject(indexKey, updates.get(key));
                    indexKey++;
                }
            }
            stmt.setObject(indexKey, updates.get("id"));
            stmt.executeUpdate();

            stmt = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
            stmt.setObject(1, updates.get("id"));
            result = stmt.executeQuery();
            if (result.next()) {
                System.out.println("RESULT: " + result);
                System.out.println(result.getString("categorie") +  " | " + result.getDouble("price"));
                Product produto = new Product();
                produto.setId(result.getString("id"));
                produto.setName(result.getString("name"));
                produto.setCategorie(result.getString("categorie"));
                produto.setPrice(result.getDouble("price"));
                produto.setImage(result.getString("image"));
                stmt.close();
                closeConnection();
                return produto;
            }
            stmt.close();
            closeConnection();
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI3: " + e);
            return null;
        }
        return null;
    }

    public boolean deleteProduct(String productID){
        try {
            openConnection();
            stmt = connection.prepareStatement("DELETE FROM products WHERE id = ?");
            stmt.setString(1, productID);
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI4: " + e);
            return false;
        }
    }


}
