package com.example.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Map;

@Document("products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends RepresentationModel<Product> {
    @Id
    private String id;

    private String productName;

    private BigDecimal price;

    private Category category;

    private String status;

    private Map<String, Integer> sizes;
}
