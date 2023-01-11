package com.example.productservice.resolver.mutation;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Validated
@AllArgsConstructor
public class CategoryMutation implements GraphQLMutationResolver {
    private final CategoryService categoryService;

    public Category addCategory(@NotBlank(message = "Category must have a non-blank name") String categoryName) {
        return categoryService.addCategory(categoryName);
    }

    public long deleteCategory(long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
