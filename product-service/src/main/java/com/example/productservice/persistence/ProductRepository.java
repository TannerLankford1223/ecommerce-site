package com.example.productservice.persistence;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByProductNameContaining(String productName, Pageable pageable);
//
//    List<Product> findProductsByProductNameContainingAndCategory(String productName, Category category, Pageable pageable);
//
//    List<Product> findByCategory(Category category, Pageable pageable);

    List<Product> findProductsByIdAfter(long id);

    List<Product> findProductsByIdAfterAndProductNameContaining(long id, String productName);

    List<Product> findProductsByIdAfterAndProductNameContainingAndCategory(long id, String productName,
                                                                              Category category);

    List<Product> findProductsByIdAfterAndCategory(long id, Category category);
}
