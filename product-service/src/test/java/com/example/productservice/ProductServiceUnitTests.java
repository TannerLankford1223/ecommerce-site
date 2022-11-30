package com.example.productservice;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.model.Status;
import com.example.productservice.persistence.ProductRepository;
import com.example.productservice.service.ProductService;
import com.example.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTests {
    @Mock
    private ProductRepository productRepo;

    private ProductService productService;

    private Product product;

    private NewProductRequest newProductRequest;

    @BeforeEach
    void initUseCase() {
        productService = new ProductServiceImpl(productRepo);

        String productId = "ABC123";
        String productName = "NewTestProduct";
        BigDecimal price = BigDecimal.valueOf(20.00);
        Category category = Category.SHIRTS;
        Status status = Status.IN_STOCK;
        HashMap<String, Integer> sizes = new HashMap<>();
        sizes.put("X-SM", 2);
        sizes.put("SM", 5);
        sizes.put("M", 3);
        sizes.put("LG", 1);
        sizes.put("X-LG", 0);

        newProductRequest = NewProductRequest.builder()
                .productName(productName)
                .price(price)
                .category(category)
                .status(status)
                .sizes(sizes)
                .build();
        product = new Product("123ABC", "NewTestProduct", BigDecimal.valueOf(20.00), Category.SHIRTS,
                Status.IN_STOCK, sizes);
    }

    @Test
    void getAllProducts_ReturnsPageOfProducts() {
        when(productRepo.findAll()).thenReturn(List.of(product));

        Page<Product> products = productService.getAllProducts();

        assertThat(products).isNotEmpty();
    }

    @Test
    void getAllProductsWithNameContaining_ReturnsPageOfProducts() {
        String searchTerm = "Test";
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByProductNameContaining(searchTerm, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsWithNameContaining(searchTerm);

        assertThat(returnedProducts).contains(product);
    }

    @Test
    void getAllProductsWithNameContaining_ReturnsEmptyPageable() {
        String searchTerm = "ThisIsASearchTerm";
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByProductNameContaining(searchTerm, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsWithNameContaining(searchTerm);

        assertThat(returnedProducts).isEmpty();
    }

    @Test
    void getAllProductsByCategory_ReturnsPageOfProducts() {
        Category searchCategory = Category.SHIRTS;
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByCategory(searchCategory, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsByCategory(searchCategory);

        assertThat(returnedProducts).contains(product);
    }

    @Test
    void getAllProductsByCategory_NoProductsInCategory() {
        Category searchCategory = Category.OUTERWEAR;
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepo.findByCategory(searchCategory, pageable)).thenReturn(productPage);

        Page<Product> returnedProducts = productService.getAllProductsByCategory(searchCategory);

        assertThat(returnedProducts).isEmpty();
    }

    @Test
    void findProductById_ReturnsAnOptionalWithProduct() {
        String productId = "ABC123";

        when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        Product returnedProduct = productService.findProductById(productId);

        assertThat(returnedProduct.getId()).isEqualTo("ABC123");
    }

    @Test
    void findProductById_IdNotFound_ThrowsAnException() {
        String productId = "FAKE543";

        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.findProductById(productId));
    }

    @Test
    void addNewProduct_AddedSuccessfully() {
        when(productRepo.save(product)).thenReturn(product);

        Product savedProduct = productService.addProduct(newProductRequest);

        assertThat(savedProduct.getProductName()).isEqualTo(product.getProductName());
    }

    @Test
    void increaseInventory_Success() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productService).increaseInventory(product.getId(), "LG");
        verify(productService, times(1)).increaseInventory(product.getId(), "LG");
    }

    @Test
    void increaseInventory_IdNotFound_ThrowsException() {
        String fakeId = "FAKEID123";
        when(productRepo.findById(fakeId)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> productService.increaseInventory(fakeId, "SM"));
    }

    @Test
    void increaseInventory_InvalidSize_ThrowsException() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        assertThrows(Exception.class, () -> productService.increaseInventory(product.getId(), "FAKE-SIZE"));
    }

    @Test
    void DecreaseInventory_Success() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productService).decreaseInventory(product.getId(), "LG");
        verify(productService, times(1)).decreaseInventory(product.getId(), "LG");
    }

    @Test
    void decreaseInventory_IdNotFound_ThrowsException() {
        String fakeId = "FAKEID123";
        when(productRepo.findById(fakeId)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> productService.decreaseInventory(fakeId, "SM"));
    }

    @Test
    void decreaseInventory_NoInventory_ThrowsException() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        assertThrows(Exception.class, () -> productService.decreaseInventory(product.getId(), "X-LG"));
    }

    @Test
    void decreaseInventory_InvalidSize_ThrowsException() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        assertThrows(Exception.class, () -> productService.decreaseInventory(product.getId(), "FAKE-SIZE"));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productService).deleteProduct(product.getId());
        verify(productService, times(1)).deleteProduct(product.getId());
    }

    @Test
    void deleteProduct_IdNotFound_ThrowsException() {
        String fakeId = "FAKEID123";
        when(productRepo.findById(fakeId)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> productService.deleteProduct(fakeId));
    }

    @Test
    void getAllCategories_ReturnsAListOfCategories() {
        List<String> categories = new ArrayList<>();
        for (Category category: Category.values()) {
            categories.add(category.toString());
        }

        List<String> returnedCategories = productService.getAllCategories();

        assertThat(returnedCategories).containsAll(categories);
    }
}
