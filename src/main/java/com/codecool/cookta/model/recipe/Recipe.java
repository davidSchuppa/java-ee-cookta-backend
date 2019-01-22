package com.codecool.cookta.model.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.PERSIST)
    private Long id;

    private String image;
    private String label;
    private String url;

    @Transient
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
