package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class WebController {

    private final EdamamAPIService requestHandler;
    private final JsonMapper jsonMapper;
    private final RegisterUserService registerUserService;
    private final LoginValidation loginValidation;
    private final UserFavourite userFavourite;

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, RegisterUserService registerUserService, LoginValidation loginValidation, UserFavourite userFavourite) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.registerUserService = registerUserService;
        this.loginValidation = loginValidation;
        this.userFavourite = userFavourite;
    }

    @GetMapping("/api")
    public List<Recipe> listRecipe() {// name this tempApiTest!
        return jsonMapper.mapFilteredJson(requestHandler.fetchData("chicken", ""));
    }


    @RequestMapping("/api/search/")
    public List<Recipe> searchByURL(HttpServletRequest request, @RequestParam String q) {
        try {
            String params = request.getQueryString();
            String searchParams = "";
            System.out.println(params);
            int startOfParams = params.indexOf('&');
            if (startOfParams != -1) {
                searchParams = params.substring(startOfParams);
            }
            return jsonMapper.mapFilteredJson(requestHandler.fetchData(q, searchParams));
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }


    @RequestMapping(value = "/cookta/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public LoginData loginUser(@RequestBody Map<String, String> data) {
        try {
            return loginValidation.validation(data);
        } catch (NullPointerException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createUserObject(@RequestBody CooktaUser cooktaUser) {
        if (registerUserService.registerUser(cooktaUser)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/add-favourite", headers = "Accept=application/json")
    public ResponseEntity<?> createFavouriteForUser(@RequestBody String name,
                                                    @RequestBody RecipeDb recipe) {
        userFavourite.addFavourite(name, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/remove-favourite", headers = "Accept=application/json")
    public ResponseEntity<?> deleteFavourite(@PathVariable("username") String name, @RequestBody RecipeDb recipe) {
        userFavourite.removeFavourite(name, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
