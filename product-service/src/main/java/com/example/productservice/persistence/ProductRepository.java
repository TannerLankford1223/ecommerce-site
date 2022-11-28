package com.example.productservice.persistence;

import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findByProductNameContaining(@RequestParam("product_name") String productName, Pageable pageable);

    Page<Product> findByCategory(@RequestParam("category") String category, Pageable pageable);
}
