package com.example.productservice.service;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.dto.ProductSearchResult;
import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.exception.ProductExistsException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    Pageable pageable = PageRequest.of(0, 10);

    @Test
    @DisplayName("Should return a paginated list of products when no search term or category is specified")
    void getProducts_NoSearchTermOrCategory_ReturnsAllProductsAsConnection() {
        SearchRequest request = SearchRequest.builder()
                .page(0)
                .size(10)
                .build();

        List<Product> products = List.of(shirt, pants);

        when(productRepo.findAll(pageable)).thenReturn(new PageImpl<>(products));

        ProductSearchResult response = productService.getProducts(request);

        assertThat(response.getProducts().size()).isEqualTo(products.size());
    }

    @Test
    @DisplayName("Should return a paginated list of products matching the search term")
    void getProducts_WithSearchTerm_ReturnsPageableOfAllProductsContainingSearchTerm() {
        SearchRequest request = SearchRequest.builder()
                .searchTerm("Shirt")
                .page(0)
                .size(10)
                .build();

        List<Product> products = List.of(shirt);

        when(productRepo.findByProductNameContaining(request.getSearchTerm().get(), pageable))
                .thenReturn(new PageImpl<>(products));

        ProductSearchResult response = productService.getProducts(request);

        assertThat(response.getProducts().size()).isEqualTo(products.size());
        assertThat(response.getProducts().get(0)).isEqualTo(products.get(0));
    }

    @Test
    @DisplayName("Should return a paginated list of all products in the category specified")
    void getProducts_WithCategory_ReturnsPageableOfAllProductsInCategory() {
        SearchRequest request = SearchRequest.builder()
                .category("Pants")
                .page(0)
                .size(10)
                .build();

        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory().get())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findByCategory(pantsCategory, pageable)).thenReturn(new PageImpl<>(products));

        ProductSearchResult response = productService.getProducts(request);

        assertThat(response.getProducts().size()).isEqualTo(products.size());
        assertThat(response.getProducts().get(0)).isEqualTo(products.get(0));
    }

    @Test
    @DisplayName("Should return a paginated list of all products containing the search term and the category specified")
    void getProducts_WithSearchTermAndCategory_ReturnsPageableOfAllProductsContainingSearchTermInCategory() {
        SearchRequest request = SearchRequest.builder()
                .searchTerm("Pants")
                .category("Pants")
                .page(0)
                .size(10)
                .build();

        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory().get())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findProductsByProductNameContainingAndCategory(request.getSearchTerm().get(), pantsCategory,
                pageable)).thenReturn(new PageImpl<>(products));

        ProductSearchResult response = productService.getProducts(request);

        assertThat(response.getProducts().size()).isEqualTo(products.size());
        assertThat(response.getProducts().get(0)).isEqualTo(products.get(0));
    }

    @Test
    @DisplayName("Should save a new product to the database")
    void addNewProduct_AddedSuccessfully() {
        String categoryName = "Shirts";
        ProductDTO productDTO = ProductDTO.builder()
                .productName("New Product")
                .price(BigDecimal.valueOf(24.99))
                .description("a new product")
                .category(categoryName)
                .build();

        Product savedProduct = new Product(5L, "New Product", BigDecimal.valueOf(24.99),
                "a new product", shirtCategory);

        when(categoryRepo.findByCategoryName(categoryName)).thenReturn(Optional.of(shirtCategory));
        when (productRepo.save(any())).thenReturn(savedProduct);

        productService.addProduct(productDTO);

        verify(productRepo, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw an error when adding a duplicate product")
    void addNewProduct_ProductExists_ThrowsException() {
        ProductDTO dupProduct = ProductDTO.builder()
                .productName("Shirt")
                .price(BigDecimal.valueOf(14.99))
                .description("a new shirt")
                .category("Shirts")
                .build();
        when(productRepo.existsProductByProductName(shirt.getProductName())).thenReturn(true);

        assertThrows(ProductExistsException.class, () -> productService.addProduct(dupProduct));
    }

    @Test
    @DisplayName("Should throw an error when adding a product with a non-existent category")
    void addNewProduct_CategoryNonExistent_ThrowsException() {
        String fakeCategory = "Fake";
        ProductDTO productDTO = ProductDTO.builder()
                .productName("Fake")
                .price(BigDecimal.valueOf(100.00))
                .description("a fake product")
                .category(fakeCategory)
                .build();

        when(categoryRepo.findByCategoryName(fakeCategory)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(productDTO));
    }

    @Test
    @DisplayName("Should update a product successfully")
    void updateProduct_Success() {
        ProductDTO productDTO = ProductDTO.builder()
                .productName(shirt.getProductName())
                .price(shirt.getPrice())
                .description("A teal shirt")
                .category("Shirts")
                .build();

        when(productRepo.findByProductName(productDTO.getProductName())).thenReturn(Optional.of(shirt));
        when(categoryRepo.findByCategoryName(productDTO.getCategory())).thenReturn(Optional.of(shirtCategory));

       Product product = productService.updateProduct(productDTO);

        verify(productRepo, times(1)).save(any());
        assertThat(product.getProductName()).isEqualTo(shirt.getProductName());
        assertThat(product.getId()).isEqualTo(shirt.getId());
        assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
    }

    @Test
    @DisplayName("Should successfully delete a product from the database")
    void deleteProduct_Success() {
        long id = 1L;
        when(productRepo.existsById(id)).thenReturn(true);

        long response = productService.deleteProduct(1L);

        verify(productRepo, times(1)).deleteById(1L);
        assertThat(response).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw an error when deleting a non-existent product")
    void deleteProduct_ProductNonExistent_ThrowsException() {
        long fakeId = 25L;
        when(productRepo.existsById(fakeId)).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> productService.deleteProduct(fakeId));
    }
}
