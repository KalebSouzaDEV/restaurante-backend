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
        List<Product> produtos = new ArrayList<>();

        try {
            openConnection();
            System.out.println("teste1");
            stmt = connection.prepareStatement("SELECT * FROM products WHERE categorie = ?");
            System.out.println("teste2");
            stmt.setString(1, categorieName);
            System.out.println("CATEGORIA NOME: " + categorieName);
            result = stmt.executeQuery();
            while (result.next()) {
                System.out.println("WTTF: " + result.getString("name"));
                Product produto = new Product(result.getString("id"), result.getString("name"), result.getDouble("price"), result.getString("categorie"), result.getString("image"));
                produtos.add(produto);
            }
        } catch (Exception e) {
            System.out.println("ERROO: " + e);
            return null;
        } finally {
            // Bloco finally para garantir que os recursos sejam sempre fechados
            try {
                if (result != null && !result.isClosed()) result.close();  // Fecha o ResultSet, se estiver aberto
                if (stmt != null && !stmt.isClosed()) stmt.close();  // Fecha o PreparedStatement, se estiver aberto
                if (connection != null && !connection.isClosed()) closeConnection();  // Fecha a conexão
                System.out.println("Recursos fechados.");
            } catch (Exception e) {
                System.out.println("Erro ao fechar recursos: " + e);  // Captura qualquer erro ao fechar os recursos
            }
        }
        return produtos;
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
            return produtos;
        } catch (Exception e) {
            System.out.println("DEU ERRO AQUI2: " + e);
            return null;
        } finally {
            try {
                if (result != null) result.close();
                if (stmt != null) stmt.close();
                if (connection != null) closeConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
