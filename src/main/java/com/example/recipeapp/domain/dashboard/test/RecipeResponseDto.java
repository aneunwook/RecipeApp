package com.example.recipeapp.domain.dashboard.test;

import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String content;
    private TestRecipeCategoryEnum category;
    private String imageUrl;
    private int likes;

    public static RecipeResponseDto from(Recipe recipe){
        return RecipeResponseDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .content(recipe.getContent())
                .category(recipe.getCategory())
                .imageUrl(recipe.getImageUrl())
                .likes(recipe.getLikes())
                .build();
    }
}

