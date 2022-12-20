package com.example.productservice.resolver.mutation;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@Validated
@AllArgsConstructor
public class CategoryMutation {
    private final CategoryService categoryService;

    public Category addCategory(@NotBlank(message = "Category must have a non-blank name") String categoryName) {
        return categoryService.addCategory(categoryName);
    }

    public long deleteCategory(long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
