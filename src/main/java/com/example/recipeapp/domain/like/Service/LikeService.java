package com.example.recipeapp.domain.like.Service;

import com.example.recipeapp.domain.like.controller.dto.LikeCountResponseDto;
import com.example.recipeapp.domain.like.controller.dto.LikeResponseDto;
import com.example.recipeapp.domain.like.domain.model.entity.Likes;
import com.example.recipeapp.domain.like.domain.repository.LikeRepository;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.repository.RecipeRepository;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.domain.user.domain.repository.UserRepository;
import com.example.recipeapp.global.exception.CustomException;
import com.example.recipeapp.global.exception.ErrorCode;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    //좋아요 등록 (좋아요가 눌리지 않은 상태만 가능)
    @Transactional
    @CacheEvict(value = "likeCount", key = "#recipeId") //캐시무효화
    public LikeResponseDto registerLike (Long userId, Long recipeId) {

        User user = userRepository.findById(userId)  ///좋아요 누른 사람 ID, (로그인된)요청한 사용자
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(recipeId)  //게시글ID로 실제 DB에 존재하는 레시피게시글 객체를 가져오기
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        //예외 (중복 좋아요 방지), 나중에 커스텀예외로 분리
        if (likeRepository.findByUserAndRecipe(user, recipe).isPresent()) {
            throw new CustomException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        //좋아요 생성, 저장
        Likes like = new Likes(user, recipe);
        likeRepository.save(like);

        // 레시피 좋아요 수 증가, 저장
        recipe.increaseLikes();
        recipeRepository.save(recipe);

        return LikeResponseDto.builder()
                .recipeId(recipeId)
                .likesCount(recipe.getLikes())
                .liked(true) //등록이므로 true
                .build();

    }


    //좋아요 취소 (좋아요가 눌려있는 상태만 가능)
    @Transactional
    @CacheEvict(value = "likeCount", key = "#recipeId")  //캐시무효화
    public LikeResponseDto cancelLike (Long userId, Long recipeId) {

        User user = userRepository.findById(userId)  ///좋아요 누른 사람 ID
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(recipeId)  //게시글ID로 실제 DB에 존재하는 레시피게시글 객체를 가져오기
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        // 이 유저가 이 게시글에 눌렀던 좋아요 엔티티 찾기, 나중에 커스텀예외로 분리
        Likes like = likeRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND)); //값이 존재하지 않아 꺼낼 수 없을 때 사용하는 예외클래스

        likeRepository.delete(like);

        // 좋아요 수 감소, 저장
        recipe.decreaseLikes();
        recipeRepository.save(recipe);

        return LikeResponseDto.builder()
                .recipeId(recipeId)
                .likesCount(recipe.getLikes())
                .liked(false) // 취소이므로 false
                .build();

    }


    //좋아요 수 조회
    @Transactional(readOnly = true)
    public LikeCountResponseDto countLikes (Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)  //게시글 존재 여부 확인
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        return LikeCountResponseDto.builder()
                .recipeId(recipeId)
                .likesCount(recipe.getLikes())
                .build();

    }

    //좋아요 수 조회 (캐시 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "likeCount", key = "#recipeId")
    public LikeCountResponseDto countLikesV2(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));


        return LikeCountResponseDto.builder()
                .recipeId(recipeId)
                .likesCount(recipe.getLikes())
                .build();

    }

}
