package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.LoginData;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.CooktaUserRepository;
import com.codecool.cookta.repository.RecipeRepository;
import com.codecool.cookta.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@Slf4j
public class WebController {

    private final EdamamAPIService requestHandler;
    private final JsonMapper jsonMapper;
    private final LoginValidation loginValidation;
    private final UserFavourite userFavourite;
    private final CooktaUserRepository cooktaUserRepository;
    private final IntoleranceSetterService intoleranceSetterService;
    private final RecipeUploadService recipeUploadService;

    @Autowired
    public WebController(EdamamAPIService requestHandler,
                         JsonMapper jsonMapper,
                         LoginValidation loginValidation,
                         UserFavourite userFavourite,
                         CooktaUserRepository cooktaUserRepository,
                         IntoleranceSetterService intoleranceSetterService,
                         RecipeUploadService recipeUploadService) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.loginValidation = loginValidation;
        this.userFavourite = userFavourite;
        this.cooktaUserRepository = cooktaUserRepository;
        this.intoleranceSetterService = intoleranceSetterService;
        this.recipeUploadService = recipeUploadService;
    }

    @RequestMapping("/api")
    public List<Recipe> tempApiTest() {
        return jsonMapper.mapFilteredJson(requestHandler.fetchData("chicken", ""));
    }


    @RequestMapping("/api/search/")
    public List<Recipe> searchByURL(HttpServletRequest request, @RequestParam String q) {
        try {
            String params = request.getQueryString();
            String searchParams = "";
            log.info("Search query string: " + params);
            int startOfParams = params.indexOf('&');
            if (startOfParams != -1) {
                searchParams = params.substring(startOfParams);
            }
            return jsonMapper.mapFilteredJson(requestHandler.fetchData(q, searchParams));
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }


    @RequestMapping(value = "/cookta/authentication", method = RequestMethod.POST)
    public LoginData loginUser(@RequestBody Map<String, String> data) {
        try {
            return loginValidation.validation(data);
        } catch (NullPointerException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/api/add-favourite", headers = "Accept=application/json")
    public ResponseEntity<?> createFavouriteForUser(@RequestBody String data) {
        ObjectMapper mapper = new ObjectMapper();
        String user = null;
        RecipeDb recipe = null;
        try {
            JsonNode dataTree = mapper.readTree(data);
            user = mapper.treeToValue(dataTree.get("user"), String.class);
            recipe = mapper.treeToValue(dataTree.path("recipe"), RecipeDb.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userFavourite.addFavourite(user, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/favourites/{username}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    public List<RecipeDb> listUserFavourites(@PathVariable("username") String username) {
        CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(username);
        List<RecipeDb> userFavourites = new ArrayList<>(user.getFavourites());
        return userFavourites;
    }


    @RequestMapping(value = "/{username}/remove-favourite", headers = "Accept=application/json")
    public ResponseEntity<?> deleteFavourite(@PathVariable("username") String name, @RequestBody RecipeDb recipe) {
        userFavourite.removeFavourite(name, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/api/intolerance/{username}", headers = "Accept=application/json")
    public ResponseEntity<?> saveUserIntolerance(@PathVariable("username") String username, @RequestBody Map<String, Map<String, Boolean>> data) throws IllegalAccessException {
        intoleranceSetterService.updateUserIntolerance(username, data);
        log.debug(username);
        log.debug(Arrays.asList(data).toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/recipe")
    public ResponseEntity<?> uploadRecipe(@RequestBody String data) throws IllegalAccessException {
        recipeUploadService.uploadRecipe(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
