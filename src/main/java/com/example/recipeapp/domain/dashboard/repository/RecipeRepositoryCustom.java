package com.example.recipeapp.domain.dashboard.repository;

import com.example.recipeapp.domain.dashboard.test.TestRecipeCategoryEnum;
import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RecipeRepositoryCustom extends JpaRepository<Recipe, Long> {
    Recipe findTop1ByCategoryOrderByLikesDescCreatedAtDesc(TestRecipeCategoryEnum category);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
