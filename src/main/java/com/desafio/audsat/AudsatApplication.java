package com.desafio.audsat;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Audsat", version = "v1", description = "This API is open"))
public class AudsatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudsatApplication.class, args);
    }

}
