package com.example.productservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
