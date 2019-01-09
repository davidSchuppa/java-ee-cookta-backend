package com.codecool.cookta.service;

import com.codecool.cookta.model.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMapper {

    public List<Recipe> mapFilteredJson(ArrayNode nodeRecipes){
        ObjectMapper mapper = new ObjectMapper();
        List<Recipe> filteredRecipes = new ArrayList<>();

        try {
            for (JsonNode node : nodeRecipes) {
                JsonNode recipeNode = node.path("recipe");
                Recipe recipe = mapper.treeToValue(recipeNode, Recipe.class);
                filteredRecipes.add(recipe);
            }
            return filteredRecipes;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}


//    ObjectMapper mapper = new ObjectMapper();
//        try {
//                JsonNode hits = mapper.readTree(new URL(apiUrl));
//                ArrayNode recipes = (ArrayNode) hits.path("hits");
//                for (JsonNode node : recipes) {
//                JsonNode recipeNode = node.path("recipe");
//                System.out.println(recipeNode);
//                Recipe recipe = mapper.treeToValue(recipeNode, Recipe.class);
//        recipeList.add(recipe);
//        }
//        System.out.println(recipeList.toString());
//        return recipeList.toString();
//
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//
//        return recipeList.toString();