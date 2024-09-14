package com.portfolioKaleb.restaurante.database;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.spec.ECField;
import java.sql.*;

public class Connect {
    public java.sql.Connection connection;
    public PreparedStatement stmt;
    public ResultSet result;
    public CallableStatement call;



    public void openConnection() throws Exception {
        String url = System.getenv("DATABASE_URL");
        String user = System.getenv("DATABASE_USER");
        String password = System.getenv("DATABASE_PASSWORD");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com banco de dados estabelecida com sucesso");
        } catch( Exception e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e);
        }
    }

    public void closeConnection() throws Exception {
        connection.close();
    }
}
