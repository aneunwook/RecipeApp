package com.example.recipeapp.domain.recipes.service;

import com.example.recipeapp.domain.recipes.cache.KeywordCounter;
import com.example.recipeapp.domain.recipes.controller.dto.*;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.example.recipeapp.domain.recipes.domain.repository.RecipeRepository;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final KeywordCounter keywordCounter;

    // 작성
    public RecipeResponse createRecipe(RecipeCreateRequest request, User user) {
        Recipe recipe = Recipe.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .build();

        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeResponse.from(savedRecipe); // 또는 new RecipeResponse(savedRecipe)
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<RecipeResponse> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(RecipeResponse::new)
                .collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public RecipeResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        return new RecipeResponse(recipe);
    }

    // 수정
    public void updateRecipe(Long recipeId, RecipeUpdateRequest request, User currentUser) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        if (!recipe.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_RECIPE_ACCESS);
        }

        recipe.update(
                request.getTitle(),
                request.getContent(),
                request.getCategory(),
                request.getImageUrl()
        );

        recipe.update(request.getTitle(), request.getContent(), request.getCategory(), request.getImageUrl());
    }

    // 삭제 (soft delete)
    public void deleteRecipe(Long recipeId, User currentUser) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        if (!recipe.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_RECIPE_ACCESS);
        }

        recipe.softDelete(); // isDeleted = true, deletedAt = now()
    }

    // 신규 레시피
    public List<RecipeResponse> getTodayRecipes() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay(); // 오늘 00:00
        LocalDateTime endOfDay = startOfDay.plusDays(1); // 내일 00:00

        List<Recipe> recipes = recipeRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);

        if (recipes.isEmpty()) {
            throw new CustomException(ErrorCode.NO_RECIPE_FOUND_TODAY);
        }

        return recipes.stream()
                .map(RecipeResponse::from)
                .toList();
    }

    //오늘의 인기 레시피(단일)
    public RecipeResponse getTodayPopularRecipeByCategory(String category) {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        return recipeRepository.findTopByCategoryAndCreatedAtBetweenOrderByLikesDesc(category, start, end)
                .map(RecipeResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_RECIPE_FOUND_TODAY));
    }

    @Transactional(readOnly = true)
    public Page<RecipeResponse> searchRecipes(String keyword, Pageable pageable) {
        if (isValidKeyword(keyword)) {
            keywordCounter.record(keyword);
        }

        return recipeRepository.search(keyword, pageable);
    }

    public List<PopularRecipe> topKeywords(int limit) {
        List<String> keywords = keywordCounter.getTopKeywords(limit);

        return keywords.stream()
                .map(keyword -> {
                    List<RecipeResponse> recipes = recipeRepository.searchTopNByKeyword(keyword, 5)
                            .stream().map(RecipeResponse::from).toList();
                    return new PopularRecipe(keyword, recipes);
                }).toList();
    }

    private boolean isValidKeyword(String keyword) {
        return StringUtils.hasText(keyword);
    }
}
