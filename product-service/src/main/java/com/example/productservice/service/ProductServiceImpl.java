package com.example.productservice.service;

import com.example.productservice.dto.NewProduct;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    @Query(name = "allProducts")
    public Page<Product> getProducts(SearchRequest searchRequest) {
        String searchTerm = searchRequest.getSearchTerm();
        String categoryName = searchRequest.getCategory();
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());

        if (!Objects.equals(searchTerm, "") && Objects.equals(categoryName, "")) {
            return productRepo.findByProductNameContaining(searchTerm, pageable);
        } else if (!Objects.equals(categoryName, "")) {
            Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);
            if (categoryOpt.isEmpty()) {
                throw new CategoryNotFoundException(categoryName);
            }

            if (!Objects.equals(searchTerm, "")) {
                return productRepo.findProductsByProductNameContainingAndCategory(searchTerm, categoryOpt.get(), pageable);
            } else {
                return productRepo.findByCategory(categoryOpt.get(), pageable);
            }
        }

        return productRepo.findAll(pageable);
    }

    @Override
    @Query(name = "productById")
    public Product getProductById(long productId) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty()) {
            throw new InvalidIdException(productId);
        }

        return productOpt.get();
    }

    @Transactional
    @Override
    public Product addProduct(NewProduct newProduct) {
        String categoryName = newProduct.getCategory();
        Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);

        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException(categoryName);
        }

        Product savedProduct = productRepo.save(new Product(newProduct.getProductName(), newProduct.getPrice(),
                newProduct.getDescription(), categoryOpt.get()));

        log.info("New Product: " + savedProduct.getProductName() + " created.");

        // TODO: Send message to inventory-service via RabbitMQ

        return savedProduct;
    }

    @Transactional
    @Override
    @Query(name = "deleteProduct")
    public long deleteProduct(long productId) {
        boolean productExists = productRepo.existsById(productId);

        if (!productExists) {
            throw new InvalidIdException(productId);
        }

        productRepo.deleteById(productId);

        log.info("Product with ID: " + productId + " deleted.");

        // TODO: Send message to inventory-service via RabbitMQ

        // Return the product ID, if nothing goes wrong, for caching purposes
        return productId;
    }
}
