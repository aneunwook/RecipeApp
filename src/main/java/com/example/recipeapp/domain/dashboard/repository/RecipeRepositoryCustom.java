package com.example.recipeapp.domain.dashboard.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RecipeRepositoryCustom extends JpaRepository<Recipe, Long> {
    Recipe findTop1ByCategoryOrderByLikesDescCreatedAtDesc(RecipeCategory category);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
