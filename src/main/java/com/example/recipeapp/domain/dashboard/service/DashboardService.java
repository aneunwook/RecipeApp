package com.example.recipeapp.domain.dashboard.service;

import com.example.recipeapp.domain.dashboard.controller.dto.CategoryBestRecipeResponseDto;
import com.example.recipeapp.domain.dashboard.controller.dto.DashboardResponseDto;
import com.example.recipeapp.domain.dashboard.controller.dto.DashboardSummaryResponseDto;
import com.example.recipeapp.domain.dashboard.repository.RecipeRepositoryCustom;
import com.example.recipeapp.domain.dashboard.test.TestRecipeCategoryEnum;

import com.example.recipeapp.domain.like.domain.model.entity.test.LikeRepository;
import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final RecipeRepositoryCustom recipeRepository;
    private final LikeRepository likeRepository;

    /**
     * 대시보드 전체 조회
     * 전체레시피, 오늘의 좋아요,오늘의 신규레시피 ->count
     * 한식,중식,일식,양식,디저트 ->카테고리 ENUM
     * 해당 카테고리의 인기 레시피1개만 -> 해당 Recipe(title,nickname,시간,좋아요,설명)
     */
    public DashboardResponseDto dashboards() {

        DashboardSummaryResponseDto summary = summary();//오늘 집계 사용

        //각 카테고리 베스트 레시피 1건씩 수집
        List<CategoryBestRecipeResponseDto> bestRecipe = Arrays.stream(TestRecipeCategoryEnum.values()).map(this::categoryRecipe)
                .collect(Collectors.toList());

        return DashboardResponseDto.builder()
                .totalRecipeCount(summary.getTotalRecipeCount())
                .todayLikeCount(summary.getTodayLikeCount())
                .todayNewRecipeCount(summary.getTodayRecipeCount())
                .categories(Arrays.asList(TestRecipeCategoryEnum.values())) //배열 -> List 컬렉션 래핑
                .bestRecipe(bestRecipe)
                .build();

    }

    /**
     * 카테고리 TOP_RECIPE 조회 로직
     * @param category
     * @return
     */
    public CategoryBestRecipeResponseDto categoryRecipe(TestRecipeCategoryEnum category) {

        Recipe recipe = recipeRepository.findTop1ByCategoryOrderByLikesDescCreatedAtDesc(category);

        if (recipe == null) {
            throw new CustomException(ErrorCode.DASHBOARD_TOPRECIPE_NOT_FOUND);
        }
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
    public DashboardSummaryResponseDto summary() {

        //당일 날짜 생성
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

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
