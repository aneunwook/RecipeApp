package com.example.recipeapp.domain.like.domain.model.entity.test;


import com.example.recipeapp.domain.like.domain.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {

    Long countByUserIdAndRecipeId(Long userId, Long recipeId);

    //대시보드 -> 오늘의 좋아요수 카운팅
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
