package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * * Configuring the new persistence layer
 */
@EnableJpaRepositories("com.example.demo.persistence.repo")//Scanning the specified package for repo
@EntityScan("com.example.demo.persistence.model")//Picking up my JPA entities
@SpringBootApplication
public class SimpleSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringApplication.class, args);
	}

}
