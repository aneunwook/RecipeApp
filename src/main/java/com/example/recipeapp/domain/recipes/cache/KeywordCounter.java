package com.example.recipeapp.domain.recipes.cache;

import java.util.List;

public interface KeywordCounter {
    void record(String keyword);
    List<String> getTopKeywords(int limit);
}
