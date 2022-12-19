package com.example.productservice.service;

import com.example.productservice.dto.NewProduct;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getProducts(SearchRequest searchRequest);

    Product getProductById(long productId);

    Product addProduct(NewProduct newProduct);

    long deleteProduct(long productId);
}
