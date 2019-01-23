package com.codecool.cookta.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    private String image;
    private String label;
    private String url;

    private String[] ingredientLines;

    @JsonCreator
    public Recipe(@JsonProperty("image") String image, @JsonProperty("label") String label, @JsonProperty("url") String url,
                  @JsonProperty("ingredientLines") String[] ingredientLines) {
        super();
        this.image = image;
        this.label = label;
        this.url = url;
        this.ingredientLines = ingredientLines;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "image='" + image + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", ingredientLines=" + Arrays.toString(ingredientLines) +
                '}';
    }
}
