package com.example.productservice.resolver;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductQuery implements GraphQLQueryResolver {
    private final ProductService productService;

    public Product productById(long productId) {
        log.info("Retrieving product id: {}", productId);
        return productService.getProductById(productId);
    }
}
