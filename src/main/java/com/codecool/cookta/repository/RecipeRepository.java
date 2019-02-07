package com.codecool.cookta.repository;

import com.codecool.cookta.model.recipe.RecipeDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeDb, Long> {

    RecipeDb findRecipeDbByUrl(String url);

    RecipeDb findRecipeDbByLabel(String label);

    boolean existsRecipeDbByUrl(String url);

    Long findIdByImage(String image);

}
