package com.example.productservice;

import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.persistence.CategoryRepository;
import com.example.productservice.service.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTests {
    @Mock
    private CategoryRepository categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getAllCategories_ReturnsAListOfCategories() {
        Category shirtCategory = new Category(1L, "Shirt");
        Category pantsCategory = new Category(2L, "Pants");
        Category jacketCategory = new Category(3L, "Jacket");
        List<Category> categories = List.of(shirtCategory, pantsCategory, jacketCategory);

        when(categoryRepo.findAll()).thenReturn(categories);

        List<Category> response = categoryService.getAllCategories();

        assertThat(response.size()).isEqualTo(categories.size());
    }

    @Test
    void addNewCategory_AddedSuccessfully() {
        final String newCategoryName = "NewCategory";

        categoryService.addCategory(newCategoryName);
        verify(categoryRepo, times(1)).save(any());
    }

    @Test
    void deleteProduct_Success() {
        when(categoryRepo.existsById(1L)).thenReturn(true);
        categoryService.deleteCategory(1L);
        verify(categoryRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_ProductNonExistent_ThrowsException() {
        when(categoryRepo.existsById(25L)).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> categoryService.deleteCategory(25L));
    }
}
