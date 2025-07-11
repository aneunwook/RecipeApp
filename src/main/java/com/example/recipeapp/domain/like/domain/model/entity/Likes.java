package com.example.recipeapp.domain.like.domain.model.entity;

import com.example.recipeapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.user.domain.model.User;


@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
        }
)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 좋아요 누른 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe; // 좋아요 대상 게시글


    public Likes(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }

}
