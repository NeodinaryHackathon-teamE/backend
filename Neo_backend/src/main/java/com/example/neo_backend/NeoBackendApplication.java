package com.example.neo_backend;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@SpringBootApplication
public class NeoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoBackendApplication.class, args);
	}

}
