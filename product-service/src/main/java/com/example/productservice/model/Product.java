package com.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Document(value = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    private String id;

    private String productName;

    private BigDecimal price;

    private Category category;

    private Status status;

    private Map<String, Integer> sizes;

    public Product(String productName, BigDecimal price, Category category,
                   Status status, Map<String, Integer> sizes) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.status = status;
        this.sizes = sizes;
    }
}
