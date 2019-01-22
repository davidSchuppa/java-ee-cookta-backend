package com.codecool.cookta.repository;

import com.codecool.cookta.model.recipe.RecipeDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeDb, Long> {
}
