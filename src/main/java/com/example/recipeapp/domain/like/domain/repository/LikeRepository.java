package com.example.recipeapp.domain.like.domain.repository;

import com.example.recipeapp.domain.like.domain.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    //특정 사용자가 특정 게시글(Post)에 대해 좋아요를 눌렀는지 조회 (중복 등록 확인, 취소 시 존재 여부 확인)
    Optional<Likes> findByUserAndRecipe(User user, Recipe recipe);

    //특정 게시글 1개에 대한 총 좋아요 수 (Likes 테이블에서 post 컬럼이 특정 값인 row들의 개수)
    //SELECT COUNT(*) FROM likes WHERE post_id = ?;
    long countByRecipe(Recipe recipe);

    //좋아요 삭제
    void deleteByUserAndRecipe(User user, Recipe recipe);

}
