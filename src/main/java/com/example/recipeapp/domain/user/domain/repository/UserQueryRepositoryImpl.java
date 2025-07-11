package com.example.recipeapp.domain.user.domain.repository;

import com.example.recipeapp.domain.user.domain.dto.UserProfileQueryDto;
import com.example.recipeapp.domain.user.domain.dto.UserRecipeQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.recipeapp.domain.recipes.domain.model.QRecipe.recipe;
import static com.example.recipeapp.domain.like.domain.model.entity.QLikes.likes;
import static com.example.recipeapp.domain.user.domain.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserProfileQueryDto getUserInfo(Long userId) {
        // 내가 작성한 레시피 수
        Long recipeCount = jpaQueryFactory
                .select(recipe.count())
                .from(recipe)
                .where(recipe.user.id.eq(userId))
                .fetchOne();

        // 내가 작성한 레시피들이 받은 총 좋아요 수
        Integer totalLikes = jpaQueryFactory
                .select(recipe.likes.sum().coalesce(0))
                .from(recipe)
                .where(recipe.user.id.eq(userId))
                .fetchOne();

        // 내가 좋아요 한 레시피 수
        Long likedRecipeCount = jpaQueryFactory
                .select(likes.count())
                .from(likes)
                .where(likes.user.id.eq(userId))
                .fetchOne();

        return new UserProfileQueryDto(
                recipeCount != null ? recipeCount.intValue() : 0,
                totalLikes != null ? totalLikes.intValue() : 0,
                likedRecipeCount != null ? likedRecipeCount.intValue() : 0
        );
    }

    // 로그인 한 사용자의 레시피 조회(정보 포함)
    @Override
    public Page<UserRecipeQueryDto> findRecipesByUserId(Long userId, Pageable pageable) {
        List<UserRecipeQueryDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        UserRecipeQueryDto.class,
                        user.id,
                        recipe.id,
                        recipe.title,
                        recipe.category,
                        recipe.imageUrl,
                        recipe.likes,
                        recipe.createdAt
                ))
                .from(recipe)
                .join(recipe.user, user)
                .where(user.id.eq(userId).and(recipe.isDeleted.eq(false)))
                .orderBy(recipe.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 페이징 처리
        Long total = jpaQueryFactory
                .select(recipe.count())
                .from(recipe)
                .where(recipe.user.id.eq(userId).and(recipe.isDeleted.eq(false)))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
