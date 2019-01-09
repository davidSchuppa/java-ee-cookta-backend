package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.codecool.cookta.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

//    String apiUrl = "https://api.edamam.com/search?q=chicken&app_id=5b5897f7&app_key=9ac6d44f07118d8a2bead5a790b270d5&from=0&to=10&calories=591-722&health=alcohol-free";
    List<Recipe> recipeList = new ArrayList<>();

    @Autowired
    private RequestHandler requestHandler;

    @RequestMapping("/")
    public String listRecipe() {
        return requestHandler.fetchData("").toString();

    }

//    @RequestMapping("/list")
//    public String listRecipes() {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            JsonNode hits = mapper.readTree(new URL(apiUrl));
//            ArrayNode recipes = (ArrayNode) hits.path("hits");
//            for (JsonNode node : recipes) {
//                JsonNode recipeNode = node.path("recipe");
//                System.out.println(recipeNode);
//                Recipe recipe = mapper.treeToValue(recipeNode, Recipe.class);
//                recipeList.add(recipe);
//            }
//            System.out.println(recipeList.toString());
//            return recipeList.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return recipeList.toString();
//    }

}
