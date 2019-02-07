package com.codecool.cookta.service;

import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.DietRepository;
import com.codecool.cookta.repository.HealthRepository;
import com.codecool.cookta.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RecipeIntolerance {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    DietRepository dietRepository;

    @Autowired
    HealthRepository healthRepository;

    @Autowired
    UserIntolerance userIntolerance;

    public void updateRecipeDietIntolerance(String label, Map<String, Boolean> diet) throws IllegalAccessException {
        RecipeDb recipe = recipeRepository.findRecipeDbByLabel(label);
        Map<String, Boolean> dietMap = userIntolerance.refresKeySet(diet);
        Diet recipeDiet = dietRepository.findDietByRecipeId(recipe.getId());
        recipeDiet.updateFields(dietMap);
        dietRepository.save(recipeDiet);
    }

    public void updateRecipeHealthIntolerance(String label, Map<String, Boolean> health) throws IllegalAccessException {
        RecipeDb recipe = recipeRepository.findRecipeDbByLabel(label);
        Map<String, Boolean> healthMap = userIntolerance.refresKeySet(health);
        Health recipeHealth = healthRepository.findByRecipeId(recipe.getId());
        recipeHealth.updateFields(healthMap);
        healthRepository.save(recipeHealth);
    }
}
