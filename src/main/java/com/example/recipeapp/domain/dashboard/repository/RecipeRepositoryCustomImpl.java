package com.example.recipeapp.domain.dashboard.repository;

import com.example.recipeapp.domain.recipes.domain.model.QRecipe;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryCustomImpl implements RecipeQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Recipe> findCategoryTopRecipeOrderByLikes(RecipeCategory category, LocalDateTime start, LocalDateTime end) {
        QRecipe recipe = QRecipe.recipe;

        BooleanBuilder builder = new BooleanBuilder();
        if (category != null)
            builder.and(recipe.category.eq(category));
        if (start != null && end != null)
            builder.and(recipe.createdAt.between(start,end));

        Recipe result = jpaQueryFactory.selectFrom(recipe)
                .where(builder)
                .orderBy(recipe.likes.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        QRecipe recipe = QRecipe.recipe;


        return jpaQueryFactory.select(recipe.count())
                .from(recipe)
                .where(recipe.createdAt.between(start,end))
                .fetchOne();
    }
}
