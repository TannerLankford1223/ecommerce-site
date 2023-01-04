package com.example.productservice.exception;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom Exception Handler for GraphQL
 *
 * @author TannnerLankford1223
 * @since 2022-11-30
 */
@Component
public class GraphQLExceptionHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream().map(this::getNested).collect(Collectors.toList());
    }

    private GraphQLError getNested(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching exception) {
            if (exception.getException() instanceof GraphQLError) {
                return (GraphQLError) exception.getException();
            }
        }
        return error;
    }
}
