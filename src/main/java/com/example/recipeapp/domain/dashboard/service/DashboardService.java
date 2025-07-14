package com.example.recipeapp.domain.dashboard.service;

import com.example.recipeapp.domain.dashboard.controller.dto.CategoryBestRecipeResponseDto;
import com.example.recipeapp.domain.dashboard.controller.dto.DashboardResponseDto;
import com.example.recipeapp.domain.dashboard.controller.dto.DashboardSummaryResponseDto;
import com.example.recipeapp.domain.dashboard.repository.RecipeRepositoryCustom;
import com.example.recipeapp.domain.like.domain.repository.LikeRepository;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DashboardService {

    private final RecipeRepositoryCustom recipeRepository;
    private final LikeRepository likeRepository;

    /**
     * 대시보드 전체 조회
     * 전체레시피, 오늘의 좋아요,오늘의 신규레시피 ->count
     * 한식,중식,일식,양식,디저트 ->카테고리 ENUM
     *DB접근
     */
    public DashboardResponseDto dashboardsV1() {

        DashboardSummaryResponseDto summaryV1 = summaryV1();//오늘 집계 사용

        return DashboardResponseDto.builder()
                .totalRecipeCount(summaryV1.getTotalRecipeCount())
                .todayLikeCount(summaryV1.getTodayLikeCount())
                .todayNewRecipeCount(summaryV1.getTodayRecipeCount())
                .categories(List.of(RecipeCategory.values()))
                .build();

    }
    //캐시 무효화 캐시 사용
    @Cacheable(value = "dashboardCache", key = "'dashboards'", cacheManager = "redisCacheManager")
    public DashboardResponseDto dashboardsV2() {

        DashboardSummaryResponseDto summaryV2 = summaryV2();//오늘 집계 사용


        return DashboardResponseDto.builder()
                .totalRecipeCount(summaryV2.getTotalRecipeCount())
                .todayLikeCount(summaryV2.getTodayLikeCount())
                .todayNewRecipeCount(summaryV2.getTodayRecipeCount())
                .categories(List.of(RecipeCategory.values()))
                .build();

    }


    /**
     * 오늘의 카테고리별  TOP_RECIPE 조회 로직
     */
    public CategoryBestRecipeResponseDto categoryRecipeV1(RecipeCategory category) {

        log.info(":::::::::::::::::::::::::::::");
        //당일 날짜 계산하기
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Recipe recipe = recipeRepository.findCategoryTopRecipeOrderByLikes(category, start, end)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_RECIPE_FOUND_TODAY));


        return CategoryBestRecipeResponseDto.builder()
                .category(category.getLabel())
                .recipeId(recipe.getId())
                .title(recipe.getTitle())
                .nickname(recipe.getUser().getNickname())
                .likes(recipe.getLikes())
                .description(recipe.getContent())
                .build();
    }

    @Cacheable(value = "categoryCache", key = "#category", cacheManager = "redisCacheManager")
    public CategoryBestRecipeResponseDto categoryRecipeV2(RecipeCategory category) {
        log.info("cache:::::::::::::::::::::::::::::");
        //당일 날짜 계산하기
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Recipe recipe = recipeRepository.findCategoryTopRecipeOrderByLikes(category,start,end)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_RECIPE_FOUND_TODAY));

        return CategoryBestRecipeResponseDto.builder()
                .category(category.getLabel())
                .recipeId(recipe.getId())
                .title(recipe.getTitle())
                .nickname(recipe.getUser().getNickname())
                .likes(recipe.getLikes())
                .description(recipe.getContent())
                .build();
    }

    /**
     * 당일 좋아요수
     * 당일 신규 레시피 수
     * 전체 레시피 수 조회하기
     * @return
     */

    public DashboardSummaryResponseDto summaryV1() {

        //당일 날짜 계산하기
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Long totalRecipeCount = recipeRepository.count();
        Long todayRecipeCount = recipeRepository.countByCreatedAtBetween(start, end);
        Long todayLikeCount = likeRepository.countByCreatedAtBetween(start, end);
        return DashboardSummaryResponseDto.builder()
                .totalRecipeCount(totalRecipeCount)
                .todayRecipeCount(todayRecipeCount)
                .todayLikeCount(todayLikeCount)
                .build();

    }

    @Cacheable(value = "summaryCache", key = "'summary'", cacheManager = "redisCacheManager")
    public DashboardSummaryResponseDto summaryV2() {

        //당일 날짜 계산하기
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Long totalRecipeCount = recipeRepository.count();
        Long todayRecipeCount = recipeRepository.countByCreatedAtBetween(start, end);
        Long todayLikeCount = likeRepository.countByCreatedAtBetween(start, end);

        return DashboardSummaryResponseDto.builder()
                .totalRecipeCount(totalRecipeCount)
                .todayRecipeCount(todayRecipeCount)
                .todayLikeCount(todayLikeCount)
                .build();

    }


}
