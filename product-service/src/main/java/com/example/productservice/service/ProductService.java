package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;

public interface ProductService {
    void addProduct(NewProductRequest newProductRequest);

    void deleteProduct(long productId);
}
