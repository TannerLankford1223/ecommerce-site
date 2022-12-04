package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);

    Page<Product> getAllProductsWithNameContaining(String productName, Pageable pageable);

    Page<Product> getAllProductsByCategory(Category category, Pageable pageable);

    Product findProductById(long productId);

    void addProduct(NewProductRequest newProductRequest);

    void deleteProduct(long productId);

    List<String> getAllCategories();
}
