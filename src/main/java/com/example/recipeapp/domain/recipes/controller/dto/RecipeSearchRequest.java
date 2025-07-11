package com.example.recipeapp.domain.recipes.controller.dto;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeSearchRequest {
    private String title;
    private RecipeCategory category;
    private String nickname;
}
