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
public class InvalidIdException extends RuntimeException implements GraphQLError {
    private final long id;
    public InvalidIdException(long id) {
        super("Id: " + id + " not found");
        this.id = id;
    }

    @Override
    public List<Object> getPath() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidField", id);
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
