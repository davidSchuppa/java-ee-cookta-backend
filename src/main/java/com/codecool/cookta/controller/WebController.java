package com.codecool.cookta.controller;

import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.EdamamAPIService;
import com.codecool.cookta.service.LoginData;
import com.codecool.cookta.service.LoginValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class WebController {

    private final EdamamAPIService requestHandler;
    private final JsonMapper jsonMapper;
    private final LoginValidation loginValidation;

    @Autowired
    public WebController(EdamamAPIService requestHandler, JsonMapper jsonMapper, LoginValidation loginValidation) {
        this.requestHandler = requestHandler;
        this.jsonMapper = jsonMapper;

        this.loginValidation = loginValidation;
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
        Map<Integer, List<String>> response = new HashMap<>();

        try {
            if(loginValidation.validation(data) != null){
                return loginValidation.validation(data);
            }else{
                return null;
            }
        } catch (NullPointerException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
