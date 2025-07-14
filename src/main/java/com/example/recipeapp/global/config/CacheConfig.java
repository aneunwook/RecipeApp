package com.example.recipeapp.global.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching   //@Cacheable을 동작하게함
public class CacheConfig {

    //내부적으로 ConcurrentHashMap을 이용해서 캐시 데이터를 저장함 (서버 메모리에 저장됨)
    @Bean
    @Primary
    public CacheManager cacheManager() {
        //Spring에서 제공하는 기본 In-Memory Cache 매니저
        return new ConcurrentMapCacheManager("likeCount","categoryCache"); //"likeCount"라는 이름의 캐시 공간을 생성
    }





}
