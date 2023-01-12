package com.example.productservice.resolver.mutation;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Validated
@AllArgsConstructor
public class ProductMutation implements GraphQLMutationResolver {
    private final ProductService productService;

    Product addProduct(ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    Product updateProduct(ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    long deleteProduct(long productId) {
        return productService.deleteProduct(productId);
    }
}
