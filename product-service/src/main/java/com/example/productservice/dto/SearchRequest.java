package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchRequest {
    private String searchTerm = "";

    private String category = "";

    private int first;

    private String after;


    public SearchRequest(int first, String after) {
        this.first = first;
        this.after = after;
    }

    public SearchRequest(String category, int first, String after) {
        this.category = category;
        this.first = first;
        this.after = after;
    }
}
