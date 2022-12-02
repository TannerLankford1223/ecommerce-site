package com.example.productservice.service;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.exception.InsufficientInventoryException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.exception.InvalidSizeException;
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
    public Product findProductById(String productId) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty()) {
            throw new InvalidIdException(productId);
        }

        return productOpt.get();
    }

    @Override
    public Product addProduct(NewProductRequest newProductRequest) {
        Product newProduct = new Product(newProductRequest.getProductName(),
                newProductRequest.getPrice(),
                newProductRequest.getCategory(),
                newProductRequest.getStatus(),
                newProductRequest.getSizes());

        return productRepo.save(newProduct);
    }

    @Override
    public void increaseInventory(String productId, String size) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty()) {
            throw new InvalidIdException(productId);
        }

        Product product = productOpt.get();
        if (!product.getSizes().containsKey(size)) {
            throw new InvalidSizeException(productId, size);
        }

        int initialSize = product.getSizes().get(size);
        product.getSizes().put(size, initialSize + 1);

        productRepo.save(product);
    }

    @Override
    public void decreaseInventory(String productId, String size) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty()) {
            throw new InvalidIdException(productId);
        }

        Product product = productOpt.get();
        if (!product.getSizes().containsKey(size)) {
            throw new InvalidSizeException(productId, size);
        }

        if (product.getSizes().get(size) <= 0) {
            throw new InsufficientInventoryException(productId, size);
        }

        int initialSize = product.getSizes().get(size);
        product.getSizes().put(size, initialSize - 1);

        productRepo.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepo.deleteById(productId);
    }

    @Override
    public List<String> getAllCategories() {
        return Stream.of(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
    }
}
