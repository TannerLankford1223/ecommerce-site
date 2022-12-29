package com.example.productservice.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductExistsException extends RuntimeException implements GraphQLError {
    public ProductExistsException(String productname) {
        super("Product with product name: " + productname + " exists.");
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.ValidationError;
    }
}
