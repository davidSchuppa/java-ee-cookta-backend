package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    String apiUrl = "https://api.edamam.com/search?q=chicken&app_id=5b5897f7&app_key=9ac6d44f07118d8a2bead5a790b270d5&from=0&to=3&calories=591-722&health=alcohol-free";
    List<Recipe> recipeList = new ArrayList<>();

    @RequestMapping("/")
    public String listRecipe() {
        RestTemplate restTemplate = new RestTemplate();
        String stringJson = restTemplate.getForObject(
                "https://api.edamam.com/search?q=chicken&app_id=5b5897f7&app_key=9ac6d44f07118d8a2bead5a790b270d5&from=0&to=3&calories=591-722&health=alcohol-free", String.class);
        JsonParser parser = new JsonParser();
        if (stringJson != null) {
            JsonObject jsonObject = (JsonObject) parser.parse(stringJson);
            JsonElement recipes = jsonObject.get("hits");
            JsonArray recipeArr = recipes.getAsJsonArray();
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(recipeArr.get(0).getAsJsonObject().get("recipe"), Recipe.class);
            System.out.println(recipe);
            return recipe.toString();
        }
        return "Error, no data";
    }

    @RequestMapping("/list")
    public String listRecipes() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode hits = mapper.readTree(new URL(apiUrl));
            ArrayNode recipes = (ArrayNode) hits.path("hits");
            for (JsonNode node : recipes) {
                JsonNode recipeNode = node.path("recipe");
                System.out.println(recipeNode);
                Recipe recipe = mapper.treeToValue(recipeNode, Recipe.class);
                recipeList.add(recipe);
            }
            System.out.println(recipeList.toString());
            return recipeList.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeList.toString();
    }

}
