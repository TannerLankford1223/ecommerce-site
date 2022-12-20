package com.example.productservice;

import com.example.productservice.dto.NewProduct;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import com.example.productservice.service.ProductServiceImpl;
import graphql.relay.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTests {
    @Mock
    private ProductRepository productRepo;

    @Mock
    private CategoryRepository categoryRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    Category pantsCategory = new Category(2L, "Pants");

    Category shirtCategory = new Category(1L, "Shirt");

    Product shirt = new Product(1L, "Shirt", BigDecimal.valueOf(20.00),
            "a shirt", shirtCategory);

    Product pants = new Product(2L, "Pants", BigDecimal.valueOf(34.99),
            "a pair of pants", pantsCategory);

    @BeforeEach
    public void init() {

    }

    @Test
    void getProducts_NoSearchTermOrCategory_ReturnsAllProductsAsConnection() {
        SearchRequest request = new SearchRequest(5, null);
        List<Product> products = List.of(shirt, pants);

        when(productRepo.findProductsByIdAfter(0)).thenReturn(products);

        Connection<Product> response = productService.getProducts(request);

        assertThat(response.getEdges().size()).isEqualTo(products.size());
    }

    @Test
    void getProducts_WithSearchTerm_ReturnsPageableOfAllProductsContainingSearchTerm() {
        SearchRequest request = new SearchRequest("Shirt", "", 5, null);
        List<Product> products = List.of(shirt);

        when(productRepo.findProductsByIdAfterAndProductNameContaining(0, request.getSearchTerm()))
                .thenReturn(products);

        Connection<Product> response = productService.getProducts(request);

        assertThat(response.getEdges().size()).isEqualTo(products.size());
        assertThat(response.getEdges().get(0).getNode()).isEqualTo(products.get(0));
    }

    @Test
    void getProducts_WithCategory_ReturnsPageableOfAllProductsInCategory() {
        SearchRequest request = new SearchRequest( "Pants", 5, null);
        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findProductsByIdAfterAndCategory(0, pantsCategory)).thenReturn(products);

        Connection<Product> response = productService.getProducts(request);

        assertThat(response.getEdges().size()).isEqualTo(products.size());
        assertThat(response.getEdges().get(0).getNode()).isEqualTo(products.get(0));
    }

    @Test
    void getProducts_WithSearchTermAndCategory_ReturnsPageableOfAllProductsContainingSearchTermInCategory() {
        SearchRequest request = new SearchRequest("Pants", "Pants", 5, null);
        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findProductsByIdAfterAndProductNameContainingAndCategory(0, request.getSearchTerm(),
                pantsCategory)).thenReturn(products);

        Connection<Product> response = productService.getProducts(request);

        assertThat(response.getEdges().size()).isEqualTo(products.size());
        assertThat(response.getEdges().get(0).getNode()).isEqualTo(products.get(0));
    }

    @Test
    void addNewProduct_AddedSuccessfully() {
        String categoryName = "Shirts";
        NewProduct newProduct = new NewProduct("NewProduct", BigDecimal.valueOf(24.99),
                "a new product", categoryName);
        Product savedProduct = new Product(5L, "NewProduct", BigDecimal.valueOf(24.99),
                "a new product", shirtCategory);

        when(categoryRepo.findByCategoryName(categoryName)).thenReturn(Optional.of(shirtCategory));
        when (productRepo.save(any())).thenReturn(savedProduct);

        productService.addProduct(newProduct);

        verify(productRepo, times(1)).save(any());
    }

    @Test
    void addNewProduct_CategoryNonExistent_ThrowsException() {
        String fakeCategory = "Fake";
        NewProduct newProduct = new NewProduct("Fake", BigDecimal.valueOf(100.00),
                "a fake product", fakeCategory);
        when(categoryRepo.findByCategoryName(fakeCategory)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(newProduct));
    }

    @Test
    void deleteProduct_Success() {
        long id = 1L;
        when(productRepo.existsById(id)).thenReturn(true);

        long response = productService.deleteProduct(1L);

        verify(productRepo, times(1)).deleteById(1L);
        assertThat(response).isEqualTo(id);
    }

    @Test
    void deleteProduct_ProductNonExistent_ThrowsException() {
        long fakeId = 25L;
        when(productRepo.existsById(fakeId)).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> productService.deleteProduct(fakeId));
    }
}
