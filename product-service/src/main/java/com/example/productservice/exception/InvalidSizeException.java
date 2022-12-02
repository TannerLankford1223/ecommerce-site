package com.example.productservice.exception;

import lombok.Getter;

@Getter
public class InvalidSizeException extends RuntimeException {
    public InvalidSizeException(String productId, String size) {
        super(size + " is not a valid size provided by the product with Id: " + productId);
    }
}
