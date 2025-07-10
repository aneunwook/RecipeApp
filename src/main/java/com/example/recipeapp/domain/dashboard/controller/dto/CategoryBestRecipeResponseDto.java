package com.example.recipeapp.domain.dashboard.controller.dto;

import com.example.recipeapp.domain.dashboard.test.TestRecipeCategoryEnum;
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
