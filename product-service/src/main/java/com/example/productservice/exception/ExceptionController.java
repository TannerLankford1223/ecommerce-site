package com.example.productservice.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom Exception Handler for Http Responses
 *
 * @author TannnerLankford1223
 * @since 2022-11-30
 */
@ControllerAdvice
@AllArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CategoryNotFoundException.class, InvalidIdException.class, Exception.class })
    public ResponseEntity<Object> handleException( RuntimeException ex, WebRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
