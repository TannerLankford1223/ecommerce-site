package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> getAllProductsWithNameContaining(String productName, Pageable pageable) {
        return productRepo.findByProductNameContaining(productName, pageable);
    }

    @Override
    public Page<Product> getAllProductsByCategory(Category category, Pageable pageable) {
        return productRepo.findByCategory(category, pageable);
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty()) {
            throw new InvalidIdException(productId);
        }

        return productOpt.get();
    }

    @Override
    public void addProduct(NewProductRequest newProductRequest) {
        Product newProduct = new Product(newProductRequest.getProductName(),
                newProductRequest.getPrice(),
                newProductRequest.getCategory());

        productRepo.save(newProduct);

        // TODO: Send message to inventory-service via RabbitMQ
    }

    @Override
    public void deleteProduct(long productId) {
        productRepo.deleteById(productId);

        // TODO: Send message to inventory-service via RabbitMQ
    }

    @Override
    public List<String> getAllCategories() {
        return Stream.of(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
    }
}
