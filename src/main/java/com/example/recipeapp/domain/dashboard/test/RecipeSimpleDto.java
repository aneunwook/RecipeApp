package com.example.recipeapp.domain.dashboard.test;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeSimpleDto {

    private Long id;
    private String title;
    private int likes;

}
