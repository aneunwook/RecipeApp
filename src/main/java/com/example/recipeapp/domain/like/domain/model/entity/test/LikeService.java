package com.example.recipeapp.domain.like.domain.model.entity.test;

import com.example.recipeapp.domain.dashboard.test.RecipeRepository;
import com.example.recipeapp.domain.like.domain.model.entity.Likes;
import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public LikeResponse likeRecipe(Long userId, Long recipeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레시피입니다."));

        // 이미 좋아요 눌렀으면 무시(카운트가 1 이상)
        if (likeRepository.countByUserIdAndRecipeId(userId, recipeId) > 0) {
            return LikeResponse.builder()
                    .recipeId(recipe.getId())
                    .totalLikes(recipe.getLikes())
                    .build();
        }

        // 좋아요 엔티티 저장 + 게시글 like 카운트 증가
        Likes like = Likes.builder()
                .user(user)
                .recipe(recipe)
                .build();
        likeRepository.save(like);

        recipe.increaseLikes();   // Recipe 엔티티 내 메서드 호출
        // JPA dirty-checking 으로 자동 flush

        return LikeResponse.builder()
                .recipeId(recipe.getId())
                .totalLikes(recipe.getLikes())
                .build();
    }

}
