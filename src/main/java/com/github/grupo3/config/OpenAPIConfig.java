package com.github.grupo3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API de integracion con GitHub")
                    .version("1.0.0")
                    .description("Esta es la documentacion de la API...")
                    .contact(new Contact()
                        .name("Soporte")
                        .email("soporte@example.com")
                        .url("https://www.example.com")
                    ));
    }
}