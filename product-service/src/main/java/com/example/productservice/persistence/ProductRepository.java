package com.example.productservice.persistence;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContaining(@RequestParam("product_name") String productName, Pageable pageable);

    Page<Product> findProductsByProductNameContainingAndCategory(@RequestParam("product_name") String productName,
                                                                 @RequestParam("category") Category category,
                                                                 Pageable pageable);

    Page<Product> findByCategory(@RequestParam("category") Category category, Pageable pageable);
}
