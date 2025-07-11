package com.example.recipeapp.domain.dashboard.controller.dto;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryBestRecipeResponseDto {

    private String category;
    private Long recipeId;
    private String title;
    private String nickname;
    private int likes;
    private String description;

}
