package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts();

    Page<Product> getAllProductsWithNameContaining(String productName);

    Page<Product> getAllProductsByCategory(Category category);

    Optional<Product> findProductById(String productId);

    void addNewProduct(NewProductRequest newProductRequest);

    void increaseInventory(String productId);

    void decreaseInventory(String productId);

    void deleteProduct(String productId);
}
