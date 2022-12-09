package com.example.productservice.exception;

import lombok.Getter;

@Getter
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(Long id) {
        super("Id: " + id + " not found");
    }
}
