package com.example.recipeapp.domain.dashboard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponseDto {

    private Long todayRecipeCount; //오늘의 레시피 카운트
    private Long totalRecipeCount; //전체 레시피 카운트
    private Long todayLikeCount; //오늘의 좋아요 수

}
