package com.example.recipeapp.domain.recipes.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PopularRecipe {

    private String keyword;
    private List<RecipeResponse> topRecipes;

}
