package com.example.productservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

/**
 * The ProductModel class extends the HATEOAS Representation Model and is required to convert the Product entity to a
 * pagination format
 *
 * @author TannnerLankford1223
 * @since 2022-12-2
 */
@Getter
@Setter
public class ProductModel extends RepresentationModel<ProductModel> {
    private String id;

    private String productName;

    private BigDecimal price;

    private Category category;
}
