package com.example.productservice.service;

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

    }

    @Override
    public void deleteCategory(long categoryId) {

    }
}
