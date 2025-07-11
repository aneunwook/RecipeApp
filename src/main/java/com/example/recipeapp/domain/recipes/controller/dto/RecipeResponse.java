package com.example.recipeapp.domain.recipes.controller.dto;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Getter;

@Getter
public class RecipeResponse {
    private Long id;
    private String title;
    private String content;
    private RecipeCategory category;
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
    public static RecipeResponse from(Recipe recipe) {
        return new RecipeResponse(recipe);
    }
}
