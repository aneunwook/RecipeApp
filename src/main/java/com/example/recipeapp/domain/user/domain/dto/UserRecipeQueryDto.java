package com.example.recipeapp.domain.user.domain.dto;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserRecipeQueryDto {

    private final Long userId;
    private final Long recipeId;
    private final String title;
    private final RecipeCategory category;
    private final String imageUrl;
    private final int likes;
    private final LocalDateTime createdAt;
}
