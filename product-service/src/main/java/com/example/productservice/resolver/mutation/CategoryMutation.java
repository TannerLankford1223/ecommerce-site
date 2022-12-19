package com.example.productservice.resolver.mutation;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CategoryMutation {
    private final CategoryService categoryService;

    public Category addCategory(String categoryName) {
        log.info("Creating category {}", categoryName);
        return categoryService.addCategory(categoryName);
    }

    public long deleteCategory(long categoryId) {
        log.info("Deleting category {}", categoryId);
        return categoryService.deleteCategory(categoryId);
    }
}
