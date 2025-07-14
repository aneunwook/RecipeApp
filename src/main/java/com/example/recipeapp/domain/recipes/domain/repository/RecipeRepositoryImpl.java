package com.example.recipeapp.domain.recipes.domain.repository;

import com.example.recipeapp.domain.recipes.controller.dto.RecipeResponse;
import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.recipeapp.domain.recipes.domain.model.QRecipe.recipe;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepositorySearchCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RecipeResponse> search(String keyword, Pageable pageable) {
        BooleanBuilder condition = buildCondition(keyword);

        List<Recipe> recipes = queryFactory
                .selectFrom(recipe)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<RecipeResponse> result = recipes.stream()
                .map(RecipeResponse::from)
                .toList();

        long total = queryFactory
                .selectFrom(recipe)
                .where(condition)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanBuilder buildCondition(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            String trimmed = keyword.trim();
            BooleanBuilder orBuilder = new BooleanBuilder();

            orBuilder.or(recipe.title.containsIgnoreCase(trimmed));
            orBuilder.or(recipe.content.containsIgnoreCase(trimmed));
            orBuilder.or(recipe.category.stringValue().containsIgnoreCase(trimmed));

            builder.and(orBuilder);

        }

        return builder;
    }

    public List<Recipe> searchTopNByKeyword(String keyword, int limit) {
        return queryFactory.selectFrom(recipe)
                .where(
                        recipe.title.containsIgnoreCase(keyword)
                                .or(recipe.content.containsIgnoreCase(keyword))
                                .or(recipe.category.stringValue().containsIgnoreCase(keyword)) // enum이니까 string으로 변환
                )
                .limit(limit)
                .fetch();
    }
}
