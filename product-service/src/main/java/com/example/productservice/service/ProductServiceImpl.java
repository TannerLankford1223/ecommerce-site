package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public void addProduct(NewProductRequest newProductRequest) {
        Optional<Category> categoryOpt = categoryRepo.findByCategoryName(newProductRequest.getCategory());

        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException(newProductRequest.getCategory());
        }

        Product newProduct = new Product(newProductRequest.getProductName(),
                newProductRequest.getPrice(),
                categoryOpt.get());

        categoryOpt.get().addProduct(newProduct);
        productRepo.save(newProduct);

        // TODO: Send message to inventory-service via RabbitMQ
    }

    @Override
    public void deleteProduct(long productId) {
        boolean productExists = productRepo.existsById(productId);

        if (!productExists) {
            throw new InvalidIdException(productId);
        }

        productRepo.deleteById(productId);

        // TODO: Send message to inventory-service via RabbitMQ
    }
}
