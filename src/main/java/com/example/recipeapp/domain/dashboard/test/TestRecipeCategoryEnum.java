package com.example.recipeapp.domain.dashboard.test;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TestRecipeCategoryEnum {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    DESSERT("디저트");

    private final String label;

    TestRecipeCategoryEnum(String label) {
        this.label = label;
    }

    @JsonValue // JSON 직렬화 시 “한식” 등 한글 문자열이 그대로 나가도록 설정
    public String getLabel(){
        return label;
    }
}
