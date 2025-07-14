package com.example.recipeapp.domain.dashboard.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.example.recipeapp.domain.recipes.domain.repository.RecipeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface RecipeRepositoryCustom extends JpaRepository<Recipe, Long> , RecipeRepository {

    //각 카테고리별 좋아요 1등의 레시피 조회
    Optional<Recipe> findCategoryTopRecipeOrderByLikes(RecipeCategory category, LocalDateTime start, LocalDateTime end);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
