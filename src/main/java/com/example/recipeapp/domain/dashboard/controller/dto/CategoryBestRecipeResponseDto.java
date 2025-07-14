package com.example.recipeapp.domain.dashboard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryBestRecipeResponseDto {

    private String category;
    private Long recipeId;
    private String title;
    private String nickname;
    private int likes;
    private String description;

}
