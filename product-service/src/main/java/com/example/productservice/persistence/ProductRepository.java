package com.example.productservice.persistence;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findProductsByProductNameContainingAndCategory(String productName, Category category, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    boolean existsProductByProductName(String productName);
}
