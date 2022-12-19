package com.example.productservice.resolver.query;

import com.example.productservice.dto.SearchRequest;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductQuery implements GraphQLQueryResolver {
    private final ProductService productService;

    public Page<Product> allProducts(SearchRequest search) {
        return productService.getProducts(search);
    }

    public Product productById(long productId) {
        return productService.getProductById(productId);
    }
}
