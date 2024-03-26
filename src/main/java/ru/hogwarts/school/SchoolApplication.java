package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition
@Log4j2
public class SchoolApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);

	}
}
