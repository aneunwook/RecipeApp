package com.example.recipeapp.domain.recipes.cache;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SearchKeywordCounter implements KeywordCounter{

    private final Map<String, AtomicInteger> keywordCountMap = new ConcurrentHashMap<>();

    // 키워드 기록, 키워드를 소문자로 변환 후 카운트 1 증가
    @Override
    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        keywordCountMap
                .computeIfAbsent(keyword.toLowerCase(), k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    // 상위 N개 인기 키워드 조회, 카운트 기준 내림차순 정렬 후 제한된 개수 반환
    @Override
    public List<String> getTopKeywords(int limit) {
        return keywordCountMap.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().get(), a.getValue().get()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}
