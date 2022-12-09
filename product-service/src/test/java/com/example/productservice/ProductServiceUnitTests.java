package com.example.productservice;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.persistence.ProductRepository;
import com.example.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

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

    long productId = 1;
    String productName = "NewTestProduct";
    BigDecimal price = BigDecimal.valueOf(20.00);
    Category category = new Category(1L, "Shirt", new ArrayList<>());

    @Test
    void addNewProduct_AddedSuccessfully() {
       final NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("NewProduct")
                .price(BigDecimal.valueOf(20.00))
                .category("Shirt")
                .build();

        when(categoryRepo.findByCategoryName(newProductRequest.getCategory())).thenReturn(Optional.of(category));
        productService.addProduct(newProductRequest);
        verify(productRepo, times(1)).save(any());
    }

    @Test
    void addNewProduct_CategoryNonExistent_ThrowsException() {
        final NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("NewProduct")
                .price(BigDecimal.valueOf(20.00))
                .category("FakeCategory")
                .build();

        when(categoryRepo.findByCategoryName(newProductRequest.getCategory())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(newProductRequest));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepo.existsById(1L)).thenReturn(true);
        productService.deleteProduct(1L);
        verify(productRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_ProductNonExistent_ThrowsException() {
        when(productRepo.existsById(25L)).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> productService.deleteProduct(25L));
    }
}
