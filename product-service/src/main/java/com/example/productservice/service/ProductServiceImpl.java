package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    public Page<Product> getAllProducts() {
        return null;
    }

    @Override
    public Page<Product> getAllProductsWithNameContaining(String productName) {
        return null;
    }

    @Override
    public Page<Product> getAllProductsByCategory(Category category) {
        return null;
    }

    @Override
    public Optional<Product> findProductById(String productId) {
        return Optional.empty();
    }

    @Override
    public void addNewProduct(NewProductRequest newProductRequest) {

    }

    @Override
    public void increaseInventory(String productId) {

    }

    @Override
    public void decreaseInventory(String productId) {

    }

    @Override
    public void deleteProduct(String productId) {

    }
}
