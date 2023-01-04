package com.example.productservice.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductExistsException extends RuntimeException implements GraphQLError {

    private final String productName;
    public ProductExistsException(String productName) {
        super("Product with product name: " + productName + " exists.");
        this.productName = productName;
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
