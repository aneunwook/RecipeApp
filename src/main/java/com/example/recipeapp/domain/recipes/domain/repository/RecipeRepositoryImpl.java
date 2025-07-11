//package com.example.recipeapp.domain.recipes.domain.repository;
//
//import com.example.recipeapp.domain.recipes.domain.model.Recipe;
//import com.example.recipeapp.domain.recipes.domain.model.QRecipe;
//import com.example.recipeapp.domain.user.domain.model.QUser;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Recipe> search(String title, String category, String nickname) {
//        QRecipe recipe = QRecipe.recipe;
//        QUser user = QUser.user;
//
//        return queryFactory.selectFrom(recipe)
//                .join(recipe.user, user).fetchJoin()
//                .where(
//                        StringUtils.hasText(title) ? recipe.title.containsIgnoreCase(title) : null,
//                        StringUtils.hasText(category) ? recipe.category.eq(category) : null,
//                        StringUtils.hasText(nickname) ? recipe.user.nickname.eq(nickname) : null
//                )
//                .fetch();
//    }
//}
