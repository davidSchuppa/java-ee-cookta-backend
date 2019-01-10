package com.codecool.cookta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class RequestHandler {

    private final String apiUrl = "https://api.edamam.com/search?q=chicken&app_id=5b5897f7&app_key=9ac6d44f07118d8a2bead5a790b270d5&from=0&to=10&calories=591-722&health=alcohol-free";


    public ArrayNode fetchData(String searchParams) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode recipes = null;
        try {
            JsonNode hits = mapper.readTree(new URL(apiUrl + searchParams));
            recipes = (ArrayNode) hits.path("hits");
            return recipes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
