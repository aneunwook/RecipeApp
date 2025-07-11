package com.example.recipeapp.domain.recipes.domain.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Optional<Recipe> findTopByCategoryAndCreatedAtBetweenOrderByLikesDesc(String category, LocalDateTime start, LocalDateTime end);
}