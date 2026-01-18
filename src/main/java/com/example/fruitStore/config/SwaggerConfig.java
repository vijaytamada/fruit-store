package com.example.fruitStore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI fruitStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fruit Store API")
                        .description("Role based API: USER / MANAGER / ADMIN")
                        .version("1.0.0"));
    }
}
