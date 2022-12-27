package com.example.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageInfo {
   private int pageNumber;

   private long totalCount;

   private int totalPages;

   private boolean hasNext;

   private boolean hasPrev;

   private Integer nextPage;

   private Integer prevPage;

}
