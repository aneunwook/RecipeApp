package com.example.recipeapp.domain.dashboard.controller;

import com.example.recipeapp.domain.dashboard.controller.dto.*;
import com.example.recipeapp.domain.dashboard.service.DashboardService;
import com.example.recipeapp.domain.dashboard.test.TestRecipeCategoryEnum;
import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import com.example.recipeapp.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 대시보드 전체 조회
     * 전체레시피, 오늘의 좋아요,오늘의 신규레시피 ->count
     * 한식,중식,일식,양식,디저트 ->카테고리 ENUM
     * 해당 카테고리의 인기 레시피1개만 -> 해당 Recipe(title,nickname,시간,좋아요,설명)
     */
    @GetMapping("/v1")
    public ResponseEntity<ApiResponse<DashboardResponseDto>> getDashboard() {
           DashboardResponseDto dto =  dashboardService.dashboards();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("전체 대시보드 조회에 성공했습니다.",dto));

    }

    /**
     * 카테고리표시 후 1등 레시피 조회
     */
    @GetMapping("/v1/{categories}")
    public ResponseEntity<ApiResponse<CategoryBestRecipeResponseDto>> categoryRecipe(
            @PathVariable("categories") TestRecipeCategoryEnum category) {
       CategoryBestRecipeResponseDto dto = dashboardService.categoryRecipe(category);
       return ResponseEntity.status(HttpStatus.OK).body(
               ApiResponse.success(category.getLabel() + "카테고리 베스트 레시피 조회 성공했습니다.",dto));
    }
    /**
     * 오늘의 레시피 앱 통계
     * todayRecipeCount -> 오늘의 신규레시피 개수
     * totalRecipeCount -> 전체 레시피 개수
     * todayLikeCount  -> 오늘의 좋아요 개수
     */
    @GetMapping("/v1/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponseDto>> getSummary() {
        DashboardSummaryResponseDto  dto = dashboardService.summary();
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("통계 조회 성공",dto));
    }



}
