package com.portfolioKaleb.restaurante;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@SpringBootApplication
public class RestauranteApplication {

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:postgresql://localhost:5432/restaurante", "postgres", "123456")
				.load();
		flyway.migrate();
		SpringApplication.run(RestauranteApplication.class, args);
	}

}
