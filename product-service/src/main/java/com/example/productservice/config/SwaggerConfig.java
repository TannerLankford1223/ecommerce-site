package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        Set<String> responseProductType = new HashSet<String>();
        responseProductType.add("application/json");
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(ResponseEntity.class)
                .produces(responseProductType)
                .consumes(responseProductType)
                .apiInfo(apiInfo());
    }

    // Replace apiInfo TOS, License, and contact info to match your organization's info
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Product REST API",
                "Ecommerce product services",
                "API",
                "Terms of service",
                "t.lankford730@gmail.com",
                "License for API",
                "API Licence URL");
        return apiInfo;
    }
}
