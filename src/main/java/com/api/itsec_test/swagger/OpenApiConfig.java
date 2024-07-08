package com.api.itsec_test.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(
                new Info()
                .title("Articles API Documentation")
                .version("1.0")
                .description("API Documentation for ITSEC Technical Test Solution")
        );
    }

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi
                .builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }
}
