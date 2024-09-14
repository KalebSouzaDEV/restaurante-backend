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
				.dataSource("jdbc:postgresql://junction.proxy.rlwy.net:30153/railway", "postgres", "OTLFCJYPNZFdsuHctuDajGlBNXmFcbnM")
				.baselineOnMigrate(true)
				.load();
		flyway.migrate();
		SpringApplication.run(RestauranteApplication.class, args);
	}


}
