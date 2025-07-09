package com.example.recipeapp.domain.like.Service;

import com.example.recipeapp.domain.like.domain.model.entity.Likes;
import com.example.recipeapp.domain.like.domain.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    //좋아요 등록 (좋아요가 눌리지 않은 상태만 가능)
    public void registerLike (Long userId, Long recipeId) {

        User user = getUserById(userId);  //좋아요 누른 사람 ID, (로그인된)요청한 사용자
        Recipe recipe = getRecipeById(recipeId);  //게시글ID로 실제 DB에 존재하는 레시피게시글 객체를 가져오기
        //user나 task가 존재하지 않으면, NullPointerException임

        //예외 (중복 좋아요 방지), 나중에 커스텀예외로 분리
        if (likeRepository.findByUserAndRecipe(user, recipe).isPresent()) {
            throw new IllegalStateException("이미 좋아요를 누른 게시글입니다.");
        }

        //좋아요 생성, 저장
        Likes like = new Likes(user, recipe);
        likeRepository.save(like);

    }


    //좋아요 취소 (좋아요가 눌려있는 상태만 가능)
    public void cancelLike (Long userId, Long recipeId) {

        User user = getUserById(userId);  //좋아요 누른 사람 ID
        Recipe recipe = getRecipeById(recipeId);  // 좋아요가 눌린 게시글

        // 이 유저가 이 게시글에 눌렀던 좋아요 엔티티 찾기, 나중에 커스텀예외로 분리
        Likes like = likeRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new NoSuchElementException("좋아요를 누른 적이 없는 게시글입니다.")); //값이 존재하지 않아 꺼낼 수 없을 때 사용하는 예외클래스

        // 좋아요 삭제
        likeRepository.delete(like);

    }


    //좋아요 수 조회
    public Long countLikes (Long recipeId) {

        Recipe recipe = getRecipeById(recipeId);  // 게시글 존재 여부 확인
        return likeRepository.countByRecipe(recipe);

    }

}
