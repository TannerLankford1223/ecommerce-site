package com.example.productservice.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CategoryNotFoundException extends RuntimeException implements GraphQLError {
    private final String categoryName;

    public CategoryNotFoundException(String categoryName) {
        super("Category with name: " + categoryName + " not found.");
        this.categoryName = categoryName;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidField", categoryName);
    }
}
