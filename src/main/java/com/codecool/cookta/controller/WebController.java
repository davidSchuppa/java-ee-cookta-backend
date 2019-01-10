package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WebController {

    private final RequestHandler requestHandler;
    private final JsonMapper jsonMapper;

    @Autowired
    public WebController(RequestHandler requestHandler, JsonMapper jsonMapper) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping("/api")
    public List<Recipe> listRecipe() {
        return jsonMapper.mapFilteredJson(requestHandler.fetchData(""));
    }


    @RequestMapping("/api/search/")
    public String searchByURL(HttpServletRequest request) {
        System.out.println(request.getQueryString());
        return "jeeeejjj";
    }

    @PostMapping
    (path = "/api/search/", consumes = "application/json", produces = "application/json")
    public List<Recipe> search(@RequestBody String reqBody) {
        System.out.println(reqBody);
        return jsonMapper.mapFilteredJson(requestHandler.fetchData("?q=chicken&app_id=5b5897f7&app_key=9ac6d44f07118d8a2bead5a790b270d5&from=3&to=6&calories=591-722&health=alcohol-free"));
    }
}
