package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class NewProduct {
    private String productName;

    private BigDecimal price;

    private String description;

    private String category;
}
