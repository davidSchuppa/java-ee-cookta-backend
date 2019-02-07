package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.payload.UploadFileResponse;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@Slf4j
public class WebController {

    private final EdamamAPIService requestHandler;
    private final JsonMapper jsonMapper;
    private final RegisterUserService registerUserService;
    private final LoginValidation loginValidation;
    private final UserFavourite userFavourite;
    private final CooktaUserRepository cooktaUserRepository;
    private final UserIntolerance userIntolerance;
    private final FileStorageService fileStorageService;
    private final RecipeRepository recipeRepository;
    private final RecipeIntolerance recipeIntolerance;

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, RegisterUserService registerUserService, LoginValidation loginValidation, UserFavourite userFavourite, CooktaUserRepository cooktaUserRepository, UserIntolerance userIntolerance, FileStorageService fileStorageService, RecipeRepository recipeRepository, RecipeIntolerance recipeIntolerance) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.registerUserService = registerUserService;
        this.loginValidation = loginValidation;
        this.userFavourite = userFavourite;
        this.cooktaUserRepository = cooktaUserRepository;
        this.userIntolerance = userIntolerance;
        this.fileStorageService = fileStorageService;
        this.recipeRepository = recipeRepository;
        this.recipeIntolerance = recipeIntolerance;
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
        userIntolerance.updateIntolerance(username, data);
        log.debug(username);
        log.debug(Arrays.asList(data).toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/recipe")
    public ResponseEntity<?> uploadRecipe(@RequestBody String data)
            throws IllegalAccessException {
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
            recipeIntolerance.updateRecipeHealthIntolerance(label, healthMap);
            recipeIntolerance.updateRecipeDietIntolerance(label, dietMap);
        } catch (IOException e) {
            e.printStackTrace();
        }


//
//        recipeRepository.save(recipe);
//        String url = "http://localhost:8080/api/userRecipe/";
//        System.out.println("itt mentettem el");
//        recipe.setUrl(url);
//        recipeRepository.save(recipe);
//        recipeIntolerance.updateRecipeIntolerance(url, healthLabel);
//        recipeIntolerance.updateRecipeIntolerance(url, dietLabel);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
