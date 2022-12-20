package com.example.productservice.service;

import com.example.productservice.connection.CursorUtil;
import com.example.productservice.dto.NewProduct;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import graphql.relay.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    @Query(name = "allProducts")
    public Connection<Product> getProducts(SearchRequest searchRequest) {
        String searchTerm = searchRequest.getSearchTerm().trim();
        String categoryName = searchRequest.getCategory().trim();
        long offset = 0;

        if (searchRequest.getAfter() != null) {
            offset = CursorUtil.decode(searchRequest.getAfter());
        }

        List<Product> products = fetchProducts(searchTerm, categoryName, offset);

        return createConnection(products, searchRequest.getFirst(), offset);
    }

    private List<Product> fetchProducts(String searchTerm, String categoryName, long offset) {

        if (!Objects.equals(searchTerm, "") && Objects.equals(categoryName, "")) {
            log.info("Retrieving products that contain the term: {}", searchTerm);
            return productRepo.findProductsByIdAfterAndProductNameContaining(offset, searchTerm);
        } else if (!Objects.equals(categoryName, "")) {
            Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);
            if (categoryOpt.isEmpty()) {
                throw new CategoryNotFoundException(categoryName);
            }

            if (!Objects.equals(searchTerm, "")) {
                log.info("Retrieving products that contain the term: {} and the category: {}", searchTerm, categoryName);
                return productRepo.findProductsByIdAfterAndProductNameContainingAndCategory(offset, searchTerm,
                        categoryOpt.get());
            } else {
                log.info("Retrieving products with category: {}", categoryName);
                return productRepo.findProductsByIdAfterAndCategory(offset, categoryOpt.get());
            }
        } else {
            return productRepo.findProductsByIdAfter(offset);
        }
    }

    private Connection<Product> createConnection(List<Product> products, int first, long cursor) {
        List<Edge<Product>> edges = products.stream()
                .map(product -> new DefaultEdge<>(product, CursorUtil.createCursorWith(product.getId())))
                .limit(first)
                .collect(Collectors.toUnmodifiableList());

        PageInfo pageInfo = new DefaultPageInfo(
                CursorUtil.getFirstCursorFrom(edges),
                CursorUtil.getLastCursorFrom(edges),
                cursor != 0,
                edges.size() >= first
        );

        return new DefaultConnection<>(edges, pageInfo);
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
