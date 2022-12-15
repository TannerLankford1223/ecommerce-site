package com.example.productservice.service;

import com.example.productservice.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(String categoryName);

    void deleteCategory(long categoryId);
}
