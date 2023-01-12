package com.example.productservice.service;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.dto.PageInfo;
import com.example.productservice.dto.ProductSearchResult;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.exception.ProductExistsException;
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

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    @Query(name = "allProducts")
    public ProductSearchResult getProducts(SearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());

        Page<Product> productPage = fetchProducts(searchRequest.getSearchTerm(), searchRequest.getCategory(), pageable);

        PageInfo pageInfo = PageInfo.builder()
                .pageNumber(productPage.getNumber())
                .totalCount(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .hasNext(productPage.hasNext())
                .hasPrev(productPage.hasPrevious())
                .nextPage(productPage.hasNext() ? productPage.getNumber() + 1 : null)
                .prevPage(productPage.hasPrevious() ? productPage.getNumber() - 1 : null)
                .build();

        return ProductSearchResult.builder()
                .products(productPage.getContent())
                .pageInfo(pageInfo)
                .build();
    }

    private Page<Product> fetchProducts(Optional<String> searchTerm, Optional<String> categoryName, Pageable pageable) {

        if (searchTerm.isPresent() && categoryName.isEmpty()) {
            log.info("Retrieving products that contain the term: {}", searchTerm.get());
            return productRepo.findByProductNameContaining(searchTerm.get(), pageable);
        } else if (categoryName.isPresent()) {
            Category category = getCategory(categoryName.get());

            if (searchTerm.isPresent()) {
                log.info("Retrieving products that contain the term: {} and the category: {}", searchTerm.get(),
                        category.getCategoryName());
                return productRepo.findProductsByProductNameContainingAndCategory(searchTerm.get(), category,
                        pageable);
            } else {
                log.info("Retrieving products with category: {}", categoryName.get());
                return productRepo.findByCategory(category, pageable);
            }
        } else {
            return productRepo.findAll(pageable);
        }
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
    public Product addProduct(ProductDTO productDTO) {
        if (productRepo.existsProductByProductName(productDTO.getProductName())) {
            throw new ProductExistsException(productDTO.getProductName());
        }

        Category category = getCategory(productDTO.getCategory());

        Product createdProduct = Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        Product savedProduct = productRepo.save(createdProduct);

        log.info("New Product: " + savedProduct.getProductName() + " created.");

        // TODO: Send message to inventory-service via RabbitMQ

        return savedProduct;
    }

    @Transactional
    @Override
    public Product updateProduct(ProductDTO productDTO) {
        Optional<Product> productOpt = productRepo.findByProductName(productDTO.getProductName());

        if (productOpt.isEmpty()) {
            addProduct(productDTO);
        }

        Category category = getCategory(productDTO.getCategory());

        Product updatedProduct = Product.builder()
                .id(productOpt.get().getId())
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        productRepo.save(updatedProduct);

        log.info("Product: {} updated", updatedProduct.getId());

        return updatedProduct;
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

        return productId;
    }

    private Category getCategory(String categoryName) {
        Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);

        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException(categoryName);
        }

        return categoryOpt.get();
    }
}
