package com.example.productservice.service;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.dto.ProductSearchResult;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.model.Product;

public interface ProductService {
    ProductSearchResult getProducts(SearchRequest searchRequest);

    Product getProductById(long productId);

    Product addProduct(ProductDTO productDTO);

    Product updateProduct(ProductDTO productDTO);

    long deleteProduct(long productId);
}
