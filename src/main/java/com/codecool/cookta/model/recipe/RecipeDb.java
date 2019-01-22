package com.codecool.cookta.model.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RecipeDb {

    @Id
    @GeneratedValue
    private Long id;

    private String image;
    private String label;
    private String url;

    @Singular
    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.PERSIST)
    private Set<IngredientLines> ingredientLines;


    @Override
    public String toString() {
        return "Recipe{" +
                "image='" + image + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", ingredientLines=" + Arrays.toString(new Set[]{ingredientLines}) +
                '}';
    }
}
