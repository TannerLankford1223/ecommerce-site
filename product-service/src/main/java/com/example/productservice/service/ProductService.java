package com.example.productservice.service;

import com.example.productservice.dto.SearchRequest;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {
    Page<Product> getProducts(SearchRequest searchRequest);

    Product getProductById(long productId);

    Product addProduct(String productName, BigDecimal price, String category);

    long deleteProduct(long productId);
}
