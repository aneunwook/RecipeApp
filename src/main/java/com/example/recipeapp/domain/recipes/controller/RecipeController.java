package com.example.recipeapp.domain.recipes.controller;

import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.recipeapp.domain.auth.domain.model.AuthUser;
import com.example.recipeapp.domain.recipes.controller.dto.RecipeCreateRequest;
import com.example.recipeapp.domain.recipes.controller.dto.RecipeResponse;
import com.example.recipeapp.domain.recipes.controller.dto.RecipeUpdateRequest;
import com.example.recipeapp.domain.recipes.service.RecipeService;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserRepository userRepository;
    // 1. 레시피 작성
    @PostMapping
    public ResponseEntity<Long> createRecipe(@RequestBody RecipeCreateRequest request, @AuthenticationPrincipal AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Long recipeId = recipeService.createRecipe(request, user);
        return ResponseEntity.ok(recipeId);
    }

    // 2. 전체 레시피 조회
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // 3. 단일 레시피 조회
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    // 4. 레시피 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @RequestBody RecipeUpdateRequest request) {
        recipeService.updateRecipe(id, request);
        return ResponseEntity.ok().build();
    }

    // 5. 레시피 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
