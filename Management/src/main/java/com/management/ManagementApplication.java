package com.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    // Bean para a conexão com o MySQL
    @Bean
    public CommandLineRunner Conexao(DataSource dataSource) {
        return args -> {
            try (var conn = dataSource.getConnection()) {
                System.out.println(" Conexão bem-sucedida com o banco: " + conn.getCatalog());
            } catch (Exception e) {
                System.err.println(" Falha na conexão: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
