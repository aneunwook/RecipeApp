package com.example.recipeapp.domain.dashboard.test;

import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    /** CREATE */
    @Transactional
    public Long create(Long userId, CreateRecipeRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"작성자 없음"));

        Recipe recipe = Recipe.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .imageUrl(requestDto.getImageUrl())
                .build();

        return recipeRepository.save(recipe).getId();
    }

    /** READ (단건) */
    public RecipeResponseDto get(Long id){
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"레시피 없음"));
        return RecipeResponseDto.from(recipe);
    }

    /** READ (카테고리별 페이지) */
    public Page<RecipeResponseDto> getByCategory(Pageable pageable, TestRecipeCategoryEnum category){
        return recipeRepository.findAllByCategory(category, pageable)
                .map(RecipeResponseDto ::from);
    }

    /** UPDATE */
    @Transactional
    public void update(Long recipeId, Long userId, UpdateRecipeReqeustDto reqeustDto){
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"레시피 없음"));

        if(!recipe.getUser().getId().equals(userId)){
            throw new ResponseStatusException(FORBIDDEN,"수정 권한 없음");
        }

        recipe.update(reqeustDto.getTitle(), reqeustDto.getContent(), reqeustDto.getCategory(), reqeustDto.getImageUrl());
    }

    /** DELETE (Soft) */
    @Transactional
    public void delete(Long recipeId, Long userId){
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"레시피 없음"));

        if(!recipe.getUser().getId().equals(userId)){
            throw new ResponseStatusException(FORBIDDEN,"삭제 권한 없음");
        }

        recipeRepository.delete(recipe);   // @SQLDelete 덕분에 Soft-Delete
    }
}
