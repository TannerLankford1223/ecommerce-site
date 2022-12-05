package com.example.productservice.controller;

import com.example.productservice.hateoas.ProductModelAssembler;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductModel;
import com.example.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final ProductModelAssembler productModelAssembler;

    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @GetMapping("/")
    public PagedModel<ProductModel> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.getAllProducts(PageRequest.of(page, size));

        return pagedResourcesAssembler.toModel(productPage, productModelAssembler);
    }

    @GetMapping("/findByNameContaining")
    public PagedModel<ProductModel> getAllWithNameContaining(@RequestParam(name = "query") String searchTerm,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/findByCategory")
    public PagedModel<ProductModel> getAllByCategory(@RequestParam(name = "cat") Category category,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/{productId}")
    public ProductModel getProduct(@PathVariable("productId") long productId) {
        return null;
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return null;
    }
}
