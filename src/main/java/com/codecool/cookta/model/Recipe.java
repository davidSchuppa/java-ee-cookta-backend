package com.codecool.cookta.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String[] ingredientLines) {
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
