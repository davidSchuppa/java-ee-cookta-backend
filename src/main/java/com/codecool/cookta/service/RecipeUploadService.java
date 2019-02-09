package com.codecool.cookta.service;

import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.RecipeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RecipeUploadService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IntoleranceSetterService intoleranceSetterService;

    public void uploadRecipe(String data) {

        log.debug(data);

        ObjectMapper mapper = new ObjectMapper();

        RecipeDb recipe = null;
        Map<String, Boolean> dietMap = new HashMap<>();
        Map<String, Boolean> healthMap = new HashMap<>();

        try {
            JsonNode dataTree = mapper.readTree(data);
            String label = mapper.treeToValue(dataTree.get("label"), String.class);
            String ingredientLines = mapper.treeToValue(dataTree.get("ingredientLines"), String.class);
            List<String> diet = mapper.treeToValue(dataTree.get("diet"), List.class);
            List<String> health = mapper.treeToValue(dataTree.get("health"), List.class);
            String fileName = mapper.treeToValue(dataTree.get("filename"), String.class);

            for (String elem : diet) {
                dietMap.put(elem, true);
            }
            for (String elem : health) {
                healthMap.put(elem, true);
            }

            String image = "http://localhost:8080/downloadFile/" + fileName;
            recipe = RecipeDb.builder().label(label).ingredientLine(ingredientLines).url(fileName).image(image).build();

            Diet defaultDiet = Diet.builder().build();
            Health defaultHealth = Health.builder().build();

            recipe.setRecipeDiet(defaultDiet);
            recipe.setRecipeHealth(defaultHealth);

            recipeRepository.save(recipe);
            intoleranceSetterService.updateRecipeHealthIntolerance(label, healthMap);
            intoleranceSetterService.updateRecipeDietIntolerance(label, dietMap);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}