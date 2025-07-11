package com.example.recipeapp.domain.recipes.controller.dto;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;

import lombok.Getter;

@Getter
public class RecipeResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private int likes;

    public RecipeResponse(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.content = recipe.getContent();
        this.category = recipe.getCategory();
        this.imageUrl = recipe.getImageUrl();
        this.likes = recipe.getLikes();
    }
}
