package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.dto.Recipe;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, RegisterUserService registerUserService, LoginValidation loginValidation, UserFavourite userFavourite, CooktaUserRepository cooktaUserRepository, UserIntolerance userIntolerance, FileStorageService fileStorageService, RecipeRepository recipeRepository) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.registerUserService = registerUserService;
        this.loginValidation = loginValidation;
        this.userFavourite = userFavourite;
        this.cooktaUserRepository = cooktaUserRepository;
        this.userIntolerance = userIntolerance;
        this.fileStorageService = fileStorageService;
        this.recipeRepository = recipeRepository;
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
            log.info("Search query string: " +params);
            int startOfParams = params.indexOf('&');
            if (startOfParams != -1) {
                searchParams = params.substring(startOfParams);
            }
            return jsonMapper.mapFilteredJson(requestHandler.fetchData(q, searchParams));
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }


    @RequestMapping(value = "/api/login", method = RequestMethod.POST, headers = "Accept=application/json")
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

    @RequestMapping(value = "/api/favourites/{username}", method = RequestMethod.GET)
    public List<RecipeDb> listUserFavourites(@PathVariable("username") String username) {
        CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(username);
        List<RecipeDb> userFavourites = new ArrayList<>(user.getFavourites());
        return userFavourites;
    }


    @RequestMapping(value = "/api/remove-favourite/{username}", headers = "Accept=application/json")
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

    @RequestMapping(value = "/api/upload-recipe/{username}")
    public ResponseEntity<?> uploadRecipe(@PathVariable("username") String username,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("label") String label,
                                          @RequestParam("ingredientLines") List<String> ingredients,
                                          @RequestParam("healthLabels") Map<String, Boolean> healthLabel,
                                          @RequestParam("dietLabels") Map<String, Boolean> dietLabel)
    {
        CooktaUser cooktaUser = cooktaUserRepository.findCooktaUserByUsername(username);
        UploadFileResponse fileResponse = fileStorageService.uploadFile(file);
        String image = fileResponse.getFileDownloadUri();
        RecipeDb recipe = RecipeDb.builder().label(label).ingredientLines(ingredients).image(image).build();
        recipeRepository.save(recipe);
        Long id = recipeRepository.findIdByImage(image);
        recipe.setUrl("http://localhost:8080/api/userRecipe/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
