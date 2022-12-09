package com.example.productservice.exception;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryName) {
        super("Category with name: " + categoryName + " not found.");
    }
}
