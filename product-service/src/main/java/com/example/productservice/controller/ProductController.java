package com.example.productservice.controller;

import com.example.productservice.dto.NewProductRequest;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductModel;
import com.example.productservice.model.ProductModelAssembler;
import com.example.productservice.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepo;

    private final ProductModelAssembler productModelAssembler;

    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @GetMapping("/")
    public PagedModel<ProductModel> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/findByNameContaining")
    public PagedModel<ProductModel> getAllWithNameContaining(@RequestParam String searchTerm,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/findByCategory")
    public PagedModel<ProductModel> getAllByCategory(@RequestParam Category category,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PostMapping("/secure/new")
    public ResponseEntity<String> addNewProduct(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody NewProductRequest newProductRequest) {
        return null;
    }

    @PutMapping("/secure/inventory/{productId}/increase")
    public ResponseEntity<String> increaseInventory(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable("productId") String productId,
                                                    @RequestParam String size) {
        return null;
    }

    @PutMapping("/secure/inventory/{productId}/decrease")
    public ResponseEntity<String> decreaseInventory(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable("productId") String productId,
                                                    @RequestParam String size) {
        return null;
    }

    @DeleteMapping("/secure/{productId}/delete")
    public ResponseEntity<String> deleteProduct(@RequestHeader(value = "Authorization") String token,
                                                @PathVariable("productId") String productId) {
        return null;
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return null;
    }
}
