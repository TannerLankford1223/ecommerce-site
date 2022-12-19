package com.example.productservice.resolver;

import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.persistence.CategoryRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryResolver implements GraphQLResolver<Product> {
    private final CategoryRepository categoryRepo;

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    // Asynchronously resolve the Category for a Product
    public CompletableFuture<Category> category(Product product) {
        String categoryName = product.getCategory().getCategoryName();

        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("Requesting category with name: {}", categoryName);
                    Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);
                    if (categoryOpt.isEmpty()) {
                        throw new CategoryNotFoundException(categoryName);
                    }
                    return categoryOpt.get();
                },
                executorService);
    }

}
