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
				.dataSource(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USER"), System.getenv("DATABASE_PASSWORD"))
				.cleanDisabled(false)
				.baselineOnMigrate(true)
				.load();
		flyway.clean();
		flyway.migrate();
		SpringApplication.run(RestauranteApplication.class, args);
	}


}
