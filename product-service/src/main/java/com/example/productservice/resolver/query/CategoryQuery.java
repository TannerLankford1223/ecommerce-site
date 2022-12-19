package com.example.productservice.resolver.query;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryQuery implements GraphQLQueryResolver {
    private final CategoryService categoryService;

    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }
}
