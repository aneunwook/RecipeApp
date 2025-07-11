package com.example.recipeapp.domain.recipes.domain.model;
import com.example.recipeapp.domain.user.domain.model.User;
import com.example.recipeapp.global.entity.BaseTimeEntity;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE recipe SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE recipe_id = ?")
@Where(clause = "is_deleted = false")
public class Recipe extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    // 작성자 연관관계 (User 엔티티)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeCategory category;

    private String imageUrl;

    private int likes = 0;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @Builder
    public Recipe(User user, String title, String content, RecipeCategory category, String imageUrl) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void update(String title, String content, RecipeCategory category, String imageUrl) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes = Math.max(0, this.likes - 1);
    }
}
