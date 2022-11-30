package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Product findProductById(String productId) {
        return null;
    }

    @Override
    public Product addProduct(NewProductRequest newProductRequest) {
        return null;
    }

    @Override
    public Product updateProduct(NewProductRequest newProductRequest) {
        return null;
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

    @Override
    public List<String> getAllCategories() {
        return null;
    }
}
