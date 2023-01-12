package com.example.productservice.persistence;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest()
@ActiveProfiles(profiles = { "test" })
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepo;

    private final Pageable pageable = PageRequest.of(0, 10);

    @Test
    @DisplayName("Should return a product containing the provided product name")
    void findByProductName_Success() {
        String productName = "grey shirt";

        Optional<Product> productOpt = productRepo.findByProductName(productName);

        assertThat(productOpt.get()).isNotNull();
        assertThat(productOpt.get().getProductName()).isEqualTo(productName);
    }

    @Test
    @DisplayName("Should return a paginated list of products containing the provided product name")
    void findByProductNameContaining_Success() {
        String productNameSearchTerm = "jacket";

        Page<Product> products = productRepo.findByProductNameContaining(productNameSearchTerm, pageable);

        System.out.println(products.getTotalElements());

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should return a paginated list of products containing the provided product name and category")
    void findProductsByProductNameContainingAndCategory_Success() {
        String productNameSearchTerm = "black";
        Category pantsCategory = Category.builder()
                .id(11)
                .categoryName("Pants")
                .build();

        Page<Product> products = productRepo.findProductsByProductNameContainingAndCategory(productNameSearchTerm,
               pantsCategory, pageable);

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(1);
        assertThat(products.getContent().get(0).getProductName()).isEqualTo("black pants");
    }

    @Test
    @DisplayName("Should return a paginated list of products containing the provided category")
    void findByCategory_Success() {
        Category shirtsCategory = Category.builder()
                .id(10)
                .categoryName("Shirts")
                .build();

        Page<Product> products = productRepo.findByCategory(shirtsCategory, pageable);

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should return True when the product that has the provided name exists")
    void existsProductByProductName_Success() {
        String productName = "green jacket";

        boolean productExists = productRepo.existsProductByProductName(productName);

        assertThat(productExists).isTrue();
    }

}
