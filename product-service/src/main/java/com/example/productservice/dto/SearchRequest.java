package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchRequest {
    private String searchTerm = "";

    private String category = "";

    private int page;

    private int size;


    public SearchRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public SearchRequest(String category, int page, int size) {
        this.category = category;
        this.page = page;
        this.size = size;
    }
}
