package com.example.recipeapp.domain.dashboard.test;

import com.example.recipeapp.domain.recipe.domain.model.Recipe;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAllByCategory(TestRecipeCategoryEnum category, Pageable pageable);

    Optional<Recipe> findByIdAndIsDeletedFalse(Long id);


}
