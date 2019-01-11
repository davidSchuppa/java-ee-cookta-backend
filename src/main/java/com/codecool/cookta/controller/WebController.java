package com.codecool.cookta.controller;

import com.codecool.cookta.model.Recipe;
import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
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
    public List<Recipe> listRecipe() {// name this tempApiTest!
        return jsonMapper.mapFilteredJson(requestHandler.fetchData("chicken", ""));
    }


    @RequestMapping("/api/search/")
    public List<Recipe> searchByURL(HttpServletRequest request, @RequestParam String q) {
        String params = request.getQueryString();
        System.out.println(params);
        int startOfParams = params.indexOf('&');
        String searchParams = params.substring(startOfParams);
        return jsonMapper.mapFilteredJson(requestHandler.fetchData(q, searchParams));
    }

}
