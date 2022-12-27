package com.example.productservice.dto;

import com.example.productservice.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProductSearchResult {
    private List<Product> products;

    private PageInfo pageInfo;


}
