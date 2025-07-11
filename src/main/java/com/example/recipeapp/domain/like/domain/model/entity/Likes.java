package com.example.recipeapp.domain.like.domain.model.entity;

import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="likes")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 좋아요 누른 사람

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe; // 좋아요 대상 게시글

    @Builder
    private Likes(User user,Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }

}
