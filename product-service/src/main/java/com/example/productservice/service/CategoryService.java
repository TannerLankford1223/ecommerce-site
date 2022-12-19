package com.example.productservice.service;

import com.example.productservice.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category addCategory(String categoryName);

    long deleteCategory(long categoryId);
}
