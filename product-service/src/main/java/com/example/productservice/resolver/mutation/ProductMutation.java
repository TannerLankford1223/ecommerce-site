package com.example.productservice.resolver.mutation;

import com.example.productservice.dto.NewProduct;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMutation implements GraphQLMutationResolver {
    private final ProductService productService;

    Product addProduct(NewProduct newProduct) {
        return productService.addProduct(newProduct);
    }

    long deleteProduct(long productId) {
        return productService.deleteProduct(productId);
    }
}
