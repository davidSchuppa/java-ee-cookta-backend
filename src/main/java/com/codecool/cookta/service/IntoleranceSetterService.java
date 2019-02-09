package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.CooktaUserRepository;
import com.codecool.cookta.repository.DietRepository;
import com.codecool.cookta.repository.HealthRepository;
import com.codecool.cookta.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class IntoleranceSetterService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CooktaUserRepository cooktaUserRepository;

    @Autowired
    DietRepository dietRepository;

    @Autowired
    HealthRepository healthRepository;


    public void updateUserIntolerance(String username, Map<String, Map<String, Boolean>> data) throws IllegalAccessException {
        CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(username);
        Long id = user.getId();
        Map<String, Boolean> diet = data.get("diet");
        Map<String, Boolean> health = data.get("health");
        Map<String, Boolean> diet2 = refreshKeySet(diet);
        Map<String, Boolean> health2 = refreshKeySet(health);

        Diet userDiet = dietRepository.findDietByCooktaUserId(id);
        Health userHealth = healthRepository.findByCooktaUserId(id);

        userDiet.updateFields(diet2);
        userHealth.updateFields(health2);

        dietRepository.save(userDiet);
        healthRepository.save(userHealth);

        System.out.println(Arrays.asList(diet2));
        System.out.println(Arrays.asList(health2));

    }

    public void updateRecipeDietIntolerance(String label, Map<String, Boolean> diet) throws IllegalAccessException {
        RecipeDb recipe = recipeRepository.findRecipeDbByLabel(label);
        Map<String, Boolean> dietMap = refreshKeySet(diet);
        Diet recipeDiet = dietRepository.findDietByRecipeId(recipe.getId());
        recipeDiet.updateFields(dietMap);
        dietRepository.save(recipeDiet);
    }

    public void updateRecipeHealthIntolerance(String label, Map<String, Boolean> health) throws IllegalAccessException {
        RecipeDb recipe = recipeRepository.findRecipeDbByLabel(label);
        Map<String, Boolean> healthMap = refreshKeySet(health);
        Health recipeHealth = healthRepository.findByRecipeId(recipe.getId());
        recipeHealth.updateFields(healthMap);
        healthRepository.save(recipeHealth);
    }

    public Map<String, Boolean> refreshKeySet(Map<String, Boolean> data){
        Map<String, Boolean> newMap = new HashMap<>();

        for(String key : data.keySet()){
            if(key.contains("-")){
                String newKey = key.replace("-","");
                newMap.put(newKey.toLowerCase(), data.get(key));
            }else {
                newMap.put(key.toLowerCase(), data.get(key));
            }
        }
        return newMap;
    }
}
