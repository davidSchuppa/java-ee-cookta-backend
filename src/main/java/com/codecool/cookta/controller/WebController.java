package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebController {

    private final RequestHandler requestHandler;
    private final JsonMapper jsonMapper;

    @Autowired
    public WebController(RequestHandler requestHandler, JsonMapper jsonMapper) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public List<Recipe> listRecipe() {
        return jsonMapper.mapFilteredJson(requestHandler.fetchData(""));

    }

}
