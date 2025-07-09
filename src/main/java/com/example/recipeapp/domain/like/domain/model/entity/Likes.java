package com.example.recipeapp.domain.like.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
        }
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 좋아요 누른 사람

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe; // 좋아요 대상 게시글

    private LocalDateTime createdAt;

}
