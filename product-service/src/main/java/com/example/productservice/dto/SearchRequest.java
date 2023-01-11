package com.example.productservice.dto;

import lombok.*;

import java.util.Optional;

//@Builder
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchRequest {
    private String searchTerm;

    private String category;

    private int page;

    private int size;

    public Optional<String> getSearchTerm() {
        return Optional.ofNullable(searchTerm);
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
