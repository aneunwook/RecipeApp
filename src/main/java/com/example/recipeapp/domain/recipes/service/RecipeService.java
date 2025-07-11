package com.example.recipeapp.domain.recipes.service;

import com.example.recipeapp.domain.recipes.controller.dto.RecipeCreateRequest;
import com.example.recipeapp.domain.recipes.controller.dto.RecipeResponse;
import com.example.recipeapp.domain.recipes.controller.dto.RecipeUpdateRequest;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.repository.RecipeRepository;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

    // 작성
    public Long createRecipe(RecipeCreateRequest request, User user) {
        Recipe recipe = Recipe.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .build();

        return recipeRepository.save(recipe).getId();
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
    public void updateRecipe(Long recipeId, RecipeUpdateRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        recipe.update(request.getTitle(), request.getContent(), request.getCategory(), request.getImageUrl());
    }

    // 삭제 (soft delete)
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        recipeRepository.delete(recipe);
    }
}
