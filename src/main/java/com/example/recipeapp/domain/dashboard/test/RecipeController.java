package com.example.recipeapp.domain.dashboard.test;

import com.example.recipeapp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /** CREATE */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody CreateRecipeRequestDto requestDto){
        return recipeService.create(requestDto);
    }

    /** READ 단건 */
    @GetMapping("/{id}")
    public RecipeResponseDto get(@PathVariable Long id){

        return recipeService.get(id);
    }

    /** READ 카테고리별(페이지) */
    @GetMapping
    public Page<RecipeResponseDto> listByCategory(@RequestParam TestRecipeCategoryEnum category,
                                                  Pageable pageable){
        return recipeService.getByCategory(pageable, category);
    }

    /** UPDATE */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @RequestParam Long userId,
                       @Valid @RequestBody UpdateRecipeReqeustDto reqeustDto){
        recipeService.update(id, userId, reqeustDto);
    }

    /** DELETE */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @RequestParam Long userId){
        recipeService.delete(id, userId);
    }

}
