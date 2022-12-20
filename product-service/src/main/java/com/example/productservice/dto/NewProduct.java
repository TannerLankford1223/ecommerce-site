package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class NewProduct {
    @NotBlank(message = "Product must have a name")
    private String productName;

    @NotNull(message = "Product must have a price")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    private String description;

    @NotNull(message = "Product must have a category")
    private String category;
}
