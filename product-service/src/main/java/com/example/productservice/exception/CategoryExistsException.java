package com.example.productservice.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryExistsException extends RuntimeException implements GraphQLError {

    public CategoryExistsException(String categoryName) {
        super("Category with name: " + categoryName + " exists.");
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
