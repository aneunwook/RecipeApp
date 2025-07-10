package com.example.recipeapp.domain.dashboard.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRecipeRequestDto {

    @NotNull
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private TestRecipeCategoryEnum category;

    private String imageUrl;
}
