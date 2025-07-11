package com.example.recipeapp.domain.user.domain.repository;

import com.example.recipeapp.domain.recipes.domain.model.Recipe;
import com.example.recipeapp.domain.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    Optional<User> findByNicknameAndIsDeletedFalse(String nickname);

    Page<User> findAllByIsDeletedFalse(Pageable pageable);

    Optional<User> findByIdAndIsDeletedFalse(Long userId);

}
