package com.example.productservice.resolver.query;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class CategoryQuery implements GraphQLQueryResolver {
    private final CategoryService categoryService;

    public List<Category> allCategories() {
        log.info("Retrieving categories");
        return categoryService.getAllCategories();
    }
}
