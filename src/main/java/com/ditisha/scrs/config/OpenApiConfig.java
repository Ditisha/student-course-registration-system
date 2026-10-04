package com.ditisha.scrs.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    // Swagger / OpenAPI documentation: http://localhost:8081/swagger-ui.html
    @Configuration
    public class OpenApiConfig {

        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI().info(new Info()
                    .title("Student Course Registration System API")
                    .version("1.0.0")
                    .description("REST APIs for student enrollment, course management and registration details"));
        }
    }