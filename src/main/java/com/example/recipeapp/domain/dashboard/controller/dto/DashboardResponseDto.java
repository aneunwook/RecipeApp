package com.example.recipeapp.domain.dashboard.controller.dto;

import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DashboardResponseDto {

    private Long todayLikeCount;        // 오늘 좋아요
    private Long todayNewRecipeCount;   // 오늘 신규 레시피
    private Long totalRecipeCount;      // 전체 레시피

    private List<RecipeCategory> categories; //카테고리 목록
    private List<CategoryBestRecipeResponseDto> bestRecipe;   // 카테고리 + 인기 레시피 1개

}
