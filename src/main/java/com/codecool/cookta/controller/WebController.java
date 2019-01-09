package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.google.gson.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebController {

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

}
