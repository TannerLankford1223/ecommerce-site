package com.example.productservice.persistence;

import com.example.productservice.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest()
@ActiveProfiles(profiles = { "test" })
public class CategoryRepositoryTests {
    @Autowired
    CategoryRepository categoryRepo;

    @Test
    @DisplayName("Should return a Category with the provided category name")
    void findByCategoryName_Success() {
        String categoryName = "Pants";

        Optional<Category> categoryOpt = categoryRepo.findByCategoryName(categoryName);

        assertThat(categoryOpt.get()).isNotNull();
        assertThat(categoryOpt.get().getCategoryName()).isEqualTo(categoryName);
    }

    @Test
    @DisplayName("Should return true if a Category with provided name exists")
    void existsCategoryByCategoryName_Success() {
        String categoryName = "Shirts";

        Boolean categoryExists = categoryRepo.existsCategoryByCategoryName(categoryName);

        assertThat(categoryExists).isTrue();
    }
}
