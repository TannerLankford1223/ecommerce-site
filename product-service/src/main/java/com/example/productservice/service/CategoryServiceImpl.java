package com.example.productservice.service;

import com.example.productservice.exception.InvalidIdException;
import com.example.productservice.model.Category;
import com.example.productservice.persistence.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Transactional
    @Override
    public Category addCategory(String categoryName) {
        Category newCategory = new Category(categoryName);
        Category response =  categoryRepo.save(newCategory);

        log.info("New Category: " + categoryName + " created.");

        return response;
    }

    @Transactional
    @Override
    public long deleteCategory(long categoryId) {
        boolean categoryExists = categoryRepo.existsById(categoryId);

        if (!categoryExists) {
            throw new InvalidIdException(categoryId);
        }

        categoryRepo.deleteById(categoryId);

        log.info("Category with ID: " + categoryId + " deleted.");

        return categoryId;
    }
}
