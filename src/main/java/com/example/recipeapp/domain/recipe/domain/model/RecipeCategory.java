package com.example.recipeapp.domain.recipes.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeCategory {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    DESSERT("디저트");

    private final String label;

    RecipeCategory(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
