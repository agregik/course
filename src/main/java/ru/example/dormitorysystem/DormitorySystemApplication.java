package ru.example.dormitorysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("ru.example.dormitorysystem.model")
@ComponentScan("ru.example.dormitorysystem")
@EnableJpaRepositories("ru.example.dormitorysystem.repository")
public class DormitorySystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(DormitorySystemApplication.class, args);
	}
}