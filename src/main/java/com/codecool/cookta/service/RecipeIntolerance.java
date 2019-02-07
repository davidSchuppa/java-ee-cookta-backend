package com.codecool.cookta.service;

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

    public void updateRecipeIntolerance(String url, Map<String, Boolean> data) throws IllegalAccessException {
        RecipeDb recipe = recipeRepository.findRecipeDbByUrl(url);
        if (data.containsKey("dietLabel")) {
            Map<String, Boolean> diet = userIntolerance.refresKeySet(data);
            Diet recipeDiet = dietRepository.findDietByRecipeId(recipe.getId());
            recipeDiet.updateFields(diet);
            dietRepository.save(recipeDiet);
        } else {
            Map<String, Boolean> health = userIntolerance.refresKeySet(data);
            Health recipeHealth = healthRepository.findByRecipeId(recipe.getId());
            recipeHealth.updateFields(health);
            healthRepository.save(recipeHealth);
        }
    }

}
