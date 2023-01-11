package com.example.productservice.resolver.mutation;

import com.example.productservice.dto.NewProduct;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Validated
@AllArgsConstructor
public class ProductMutation implements GraphQLMutationResolver {
    private final ProductService productService;

    Product addProduct(@Valid NewProduct newProduct) {
        return productService.addProduct(newProduct);
    }

    long deleteProduct(long productId) {
        return productService.deleteProduct(productId);
    }
}
