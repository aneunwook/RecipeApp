package com.example.recipeapp.domain.recipes.controller.dto;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecipeSummaryResponse {
    private Long recipeId;
    private String title;
    private RecipeCategory category;
    private String imageUrl;
    private int likes;
    private LocalDateTime createdAt;
    private String username;

    public static RecipeSummaryResponse from(Recipe recipe) {
        return RecipeSummaryResponse.builder()
                .recipeId(recipe.getId())
                .title(recipe.getTitle())
                .category(recipe.getCategory())
                .imageUrl(recipe.getImageUrl())
                .likes(recipe.getLikes())
                .createdAt(recipe.getCreatedAt())
                .username(recipe.getUser().getUsername())
                .build();
    }
}
