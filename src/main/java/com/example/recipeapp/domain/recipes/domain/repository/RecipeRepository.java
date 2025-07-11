package com.example.recipeapp.domain.recipes.domain.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
