package com.example.productservice;

import com.example.productservice.dto.SearchRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
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

    private final Category shirtCategory = new Category(1L, "Shirt");
    private final Category pantsCategory = new Category(2L, "Pants");
    private final Category jacketCategory = new Category(3L, "Jacket");

    private final Product shirt = new Product(1L, "Shirt", BigDecimal.valueOf(20.00),
            "a shirt", shirtCategory);
    private final Product pants = new Product(2L, "Pants", BigDecimal.valueOf(34.99),
            "a pair of pants", pantsCategory);
    private final Product jacket = new Product (3L, "Jacket", BigDecimal.valueOf(50.00),
            "a jacket", jacketCategory);

    @Test
    void getProducts_NoSearchTermOrCategory_ReturnsAllProductsAsPageable() {
        SearchRequest request = new SearchRequest(0, 5);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Product> products = List.of(shirt, pants, jacket);

        when(productRepo.findAll(pageable)).thenReturn(new PageImpl<>(products));

        Page<Product> response = productService.getProducts(request);

        assertThat(response.getTotalElements()).isEqualTo(products.size());
    }

    @Test
    void getProducts_WithSearchTerm_ReturnsPageableOfAllProductsContainingSearchTerm() {
        SearchRequest request = new SearchRequest("Shirt", "", 0, 5);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Product> products = List.of(shirt);

        when(productRepo.findByProductNameContaining(request.getSearchTerm(), pageable))
                .thenReturn(new PageImpl<>(products));

        Page<Product> response = productService.getProducts(request);

        assertThat(response.getSize()).isEqualTo(products.size());
        assertThat(response.getContent().get(0)).isEqualTo(products.get(0));
    }

    @Test
    void getProducts_WithCategory_ReturnsPageableOfAllProductsInCategory() {
        SearchRequest request = new SearchRequest( "Pants", 0, 5);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findByCategory(pantsCategory, pageable)).thenReturn(new PageImpl<>(products));

        Page<Product> response = productService.getProducts(request);

        assertThat(response.getSize()).isEqualTo(products.size());
        assertThat(response.getContent().get(0)).isEqualTo(products.get(0));
    }

    @Test
    void getProducts_WithSearchTermAndCategory_ReturnsPageableOfAllProductsContainingSearchTermInCategory() {
        SearchRequest request = new SearchRequest("Pants", "Pants", 0, 5);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Product> products = List.of(pants);

        when(categoryRepo.findByCategoryName(request.getCategory())).thenReturn(Optional.of(pantsCategory));
        when(productRepo.findProductsByProductNameContainingAndCategory(request.getSearchTerm(), pantsCategory, pageable))
                .thenReturn(new PageImpl<>(products));

        Page<Product> response = productService.getProducts(request);

        assertThat(response.getNumberOfElements()).isEqualTo(products.size());
        assertThat(response.getContent().get(0)).isEqualTo(products.get(0));
    }

    @Test
    void addNewProduct_AddedSuccessfully() {
        String categoryName = "Shirts";
        Product savedProduct = new Product(5L, "NewProduct", BigDecimal.valueOf(24.99)
                , "a new product", shirtCategory);
        when(categoryRepo.findByCategoryName(categoryName)).thenReturn(Optional.of(shirtCategory));
        when (productRepo.save(any())).thenReturn(savedProduct);

        productService.addProduct("NewProduct", BigDecimal.valueOf(24.99), categoryName);

        verify(productRepo, times(1)).save(any());
    }

    @Test
    void addNewProduct_CategoryNonExistent_ThrowsException() {
        String fakeCategory = "Fake";

        when(categoryRepo.findByCategoryName(fakeCategory)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct("NewProduct",
                BigDecimal.valueOf(39.99), "Fake"));
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
