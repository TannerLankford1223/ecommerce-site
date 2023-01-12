package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String productName;

    private BigDecimal price;

    private String description;

    private String category;

    public String getProductName() {
        return productName.toLowerCase();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description.toLowerCase();
    }

    public String getCategory() {
        return category;
    }
}
