package com.example.recipeapp.domain.like.domain.model.entity.test;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardTotalResponseDto {

    private Long totalRecipe;
    private Long todayNewRecipe;
    private Long todayLikes;
}
