package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.CooktaUserRepository;
import com.codecool.cookta.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFavourite {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public void addFavourite(String username, RecipeDb recipe) {
        CooktaUser cooktaUserByUsername = cooktaUserRepository.findCooktaUserByUsername(username);
        if (recipeRepository.existsRecipeDbByUrl(recipe.getUrl())) {
            recipe = recipeRepository.findRecipeDbByUrl(recipe.getUrl());
        } else {
            recipeRepository.save(recipe);
        }
        cooktaUserByUsername.appendFavourite(recipe);
    }

}
