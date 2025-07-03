package com.trimblecars.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI trimbleCarsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trimble Cars API")
                        .description("Car Lease Management API for Trimble Cars")
                        .version("v1.0"));
    }
}
