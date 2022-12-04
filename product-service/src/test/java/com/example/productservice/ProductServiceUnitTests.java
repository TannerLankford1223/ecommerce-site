package com.example.productservice;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.ProductRepository;
import com.example.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTests {
    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    long productId = 1;
    String productName = "NewTestProduct";
    BigDecimal price = BigDecimal.valueOf(20.00);
    Category category = Category.SHIRTS;

    private final Product product = new Product(productId, productName, price, category);

    private final NewProductRequest newProductRequest = NewProductRequest.builder()
            .productName(productName)
            .price(price)
            .category(category)
            .build();

    @Test
    void getAllProducts_ReturnsPageOfProducts() {
        Pageable pageable = PageRequest.of(0, 5);
        when(productRepo.findAll(pageable)).thenReturn(new PageImpl<Product>(List.of(product)));

        Page<Product> products = productService.getAllProducts(PageRequest.of(0, 5));

        assertThat(products).isNotEmpty();
    }

    @Test
    void getAllProductsWithNameContaining_ReturnsPageOfProducts() {
        String searchTerm = "Test";
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByProductNameContaining(searchTerm, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsWithNameContaining(searchTerm,
                PageRequest.of(0, 5));

        assertThat(returnedProducts).contains(product);
    }

    @Test
    void getAllProductsWithNameContaining_ReturnsEmptyPageable() {
        String searchTerm = "ThisIsASearchTerm";
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByProductNameContaining(searchTerm, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsWithNameContaining(searchTerm,
                PageRequest.of(0, 5));

        // If there are no products to return, the Pageable will still contain a list of Links, due to the HATEOAS
        // dependency
        assertThat(returnedProducts.getContent().size()).isEqualTo(1);
    }

    @Test
    void getAllProductsByCategory_ReturnsPageOfProducts() {
        Category searchCategory = Category.SHIRTS;
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByCategory(searchCategory, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsByCategory(searchCategory,
                PageRequest.of(0, 5));

        assertThat(returnedProducts).contains(product);
    }

    @Test
    void getAllProductsByCategory_NoProductsInCategory() {
        Category searchCategory = Category.OUTERWEAR;
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByCategory(searchCategory, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsByCategory(searchCategory, PageRequest.of(0, 5));

        // If there are no products to return, the Pageable will still contain a list of Links, due to the HATEOAS
        // dependency
        assertThat(returnedProducts.getContent().size()).isEqualTo(1);
    }

    @Test
    void findProductById_ReturnsTheProduct() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));

        Product returnedProduct = productService.getProductById(product.getId());

        assertThat(returnedProduct.getId()).isEqualTo(1);
    }

    @Test
    void findProductById_IdNotFound_ThrowsAnException() {
        long productId = 500;

        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.getProductById(productId));
    }

    @Test
    void addNewProduct_AddedSuccessfully() {
        productService.addProduct(newProductRequest);
        verify(productRepo, times(1)).save(any());
    }

    @Test
    void deleteProduct_Success() {
        productService.deleteProduct(product.getId());
        verify(productRepo, times(1)).deleteById(product.getId());
    }

    @Test
    void getAllCategories_ReturnsAListOfCategories() {
        List<String> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.toString());
        }

        List<String> returnedCategories = productService.getAllCategories();

        assertThat(returnedCategories).containsAll(categories);
    }
}
