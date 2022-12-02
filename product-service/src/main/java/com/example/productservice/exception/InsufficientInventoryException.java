package com.example.productservice.exception;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String productId, String size) {
        super("Cannot decrease the quantity of the size " + size + " for product with Id: " + productId +
                " due to insufficient inventory");
    }
}
