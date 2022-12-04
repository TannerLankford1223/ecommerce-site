package com.example.productservice.dto;

import com.example.productservice.model.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class NewProductRequest {
    private String productName;

    private BigDecimal price;

    private Category category;

    private Map<String, Integer> sizes;
}
