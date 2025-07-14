package com.example.recipeapp.domain.dashboard.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecipeQueryRepository {
    //각 카테고리별 좋아요 1등의 레시피 조회
    Optional<Recipe> findCategoryTopRecipeOrderByLikes(RecipeCategory category, LocalDateTime start, LocalDateTime end);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
