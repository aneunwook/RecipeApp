package com.example.recipeapp.domain.recipes.controller.dto;

import lombok.Getter;

@Getter
public class RecipeUpdateRequest {
    private String title;
    private String content;
    private String category;
    private String imageUrl;
}
