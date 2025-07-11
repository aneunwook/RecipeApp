package com.example.recipeapp.domain.recipes.controller.dto;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Getter;

@Getter
public class RecipeUpdateRequest {
    private String title;
    private String content;
    private RecipeCategory category;
    private String imageUrl;
}
