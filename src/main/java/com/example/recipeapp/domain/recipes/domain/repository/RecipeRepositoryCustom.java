package com.example.recipeapp.domain.recipes.domain.repository;

import com.example.recipeapp.domain.recipes.controller.dto.RecipeResponse;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeRepositorySearchCustom {
    Page<RecipeResponse> search(String keyword, Pageable pageable);

    List<Recipe> searchTopNByKeyword(String keyword, int limit);


}
