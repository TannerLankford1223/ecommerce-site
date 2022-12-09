package com.example.productservice.service;

import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.persistence.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    @Override
    public void addCategory(String categoryName) {
        Category newCategory = new Category(categoryName);
        categoryRepo.save(newCategory);

        log.info("New Category: " + categoryName + " created.");
    }

    @Override
    public void deleteCategory(long categoryId) {
        boolean categoryExists = categoryRepo.existsById(categoryId);

        if (!categoryExists) {
            throw new InvalidIdException(categoryId);
        }

        categoryRepo.deleteById(categoryId);

        log.info("Category with ID: " + categoryId + " deleted.");
    }
}
