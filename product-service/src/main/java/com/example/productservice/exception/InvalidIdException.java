package com.example.productservice.exception;

import lombok.Getter;

@Getter
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String productId) {
        super("Property with Id: " + productId + " not found");
    }
}
