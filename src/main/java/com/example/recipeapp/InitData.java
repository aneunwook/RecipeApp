//package com.example.recipeapp;
//
//import com.example.recipeapp.domain.like.domain.model.entity.Likes;
//import com.example.recipeapp.domain.like.domain.repository.LikeRepository;
//import com.example.recipeapp.domain.recipes.domain.model.Recipe;
//import com.example.recipeapp.domain.recipes.domain.model.RecipeCategory;
//import com.example.recipeapp.domain.recipes.domain.repository.RecipeRepository;
//import com.example.recipeapp.domain.user.domain.model.User;
//import com.example.recipeapp.domain.user.domain.model.UserRoleEnum;
//import com.example.recipeapp.domain.user.domain.repository.UserRepository;
//import com.example.recipeapp.domain.user.service.UserService;
//import com.example.recipeapp.global.security.PasswordEncoder;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Random;
//
//@Component
//@RequiredArgsConstructor
//public class InitData {
//    private final PasswordEncoder passwordEncoder;
//    private final UserService userService;
//    private final UserRepository userRepository;
//    private final RecipeRepository recipeRepository;
//    private final LikeRepository likeRepository;
//
//    private final Random random = new Random(); // ✅ Random 객체 추가
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        initUsers();
//        initRecipes();
//        List<User> userList = userRepository.findAll();
//        List<Recipe> recipeList = recipeRepository.findAll();
//        initLikes(recipeList, userList);
//    }
//
//    public void initUsers() {
//        for (int i = 1; i <= 5000; i++) {
//            String username = "user" + i;
//            String nickname = "nickname" + i;
//            String email = "user" + i + "@example.com";
//            String password = passwordEncoder.encode("1234");
//            UserRoleEnum role = (i % 2 == 0) ? UserRoleEnum.USER : UserRoleEnum.ADMIN;
//
//            User user = User.builder()
//                    .username(username)
//                    .nickname(nickname)
//                    .email(email)
//                    .password(password)
//                    .role(role)
//                    .build();
//            userService.save(user);
//        }
//    }
//
//    public void initRecipes() {
//        List<User> userList = userRepository.findAll();
//        RecipeCategory[] category = RecipeCategory.values();
//
//        for (int i = 1; i <= 50000; i++) {
//            String title = "title" + i;
//            String content = "content" + i;
//            String imageUrl = "http://example.com/image" + i + ".jpg";
//
//            User randomUser = userList.get(random.nextInt(userList.size()));
//            RecipeCategory randomCategory = category[random.nextInt(category.length)];
//            int randomLikes = random.nextInt(500); // 0~499
//
//            Recipe recipe = Recipe.builder()
//                    .user(randomUser)
//                    .title(title)
//                    .content(content)
//                    .category(randomCategory)
//                    .imageUrl(imageUrl)
//                    .build();
//
//            for (int j = 0; j < randomLikes; j++) {
//                recipe.increaseLikes();
//            }
//
//            recipeRepository.save(recipe); //  저장
//        }
//    }
//
//    public void initLikes(List<Recipe> recipeList, List<User> userList) {
//        LocalDateTime now = LocalDateTime.now();
//        for (int i = 0; i <= 50000; i++) {
//            User randomUser = userList.get(random.nextInt(userList.size()));
//            Recipe randomRecipe = recipeList.get(random.nextInt(recipeList.size()));
//
//            if (likeRepository.existsByUserAndRecipe(randomUser, randomRecipe)) continue;
//
//
//            Likes like = new Likes(randomUser,randomRecipe);
//
//            likeRepository.save(like);
//        }
//    }
//}
