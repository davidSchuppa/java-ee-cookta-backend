package com.codecool.cookta.repository;

import com.codecool.cookta.model.recipe.IngredientLines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientLinesRepository extends JpaRepository<IngredientLines, Long> {
}
