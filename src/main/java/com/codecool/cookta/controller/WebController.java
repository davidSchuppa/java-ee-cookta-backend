package com.codecool.cookta.controller;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.EdamamAPIService;
import com.codecool.cookta.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class WebController {

    private final EdamamAPIService requestHandler;
    private final JsonMapper jsonMapper;
    private final RegisterUserService registerUserService;

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, RegisterUserService registerUserService) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
        this.registerUserService = registerUserService;
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

    @RequestMapping(value = "/api/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createUserObject(@RequestBody CooktaUser cooktaUser) {
        if (registerUserService.registerUser(cooktaUser)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
