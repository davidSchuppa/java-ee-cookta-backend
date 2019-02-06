package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.CooktaUserRepository;
import com.codecool.cookta.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final CooktaUserRepository cooktaUserRepository;
    private final UserIntolerance userIntolerance;

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, RegisterUserService registerUserService, LoginValidation loginValidation, UserFavourite userFavourite, CooktaUserRepository cooktaUserRepository, UserIntolerance userIntolerance) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.registerUserService = registerUserService;
        this.loginValidation = loginValidation;
        this.userFavourite = userFavourite;
        this.cooktaUserRepository = cooktaUserRepository;
        this.userIntolerance = userIntolerance;
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

    @RequestMapping(value = "/favourites/{username}", method = RequestMethod.GET)
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


    @RequestMapping(value = "/intolerance/{username}", headers = "Accept=application/json")
    public ResponseEntity<?> saveUserIntolerance(@PathVariable("username") String username, @RequestBody Map<String, Map<String, Boolean>> data) throws IllegalAccessException {
        userIntolerance.updateIntolerance(username, data);
        System.out.println(username);
        System.out.println(Arrays.asList(data));
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
