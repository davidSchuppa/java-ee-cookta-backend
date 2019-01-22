package com.codecool.cookta.model.recipe;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;


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


    /*@Singular
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Favourite> favourites;*/


    @Singular
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private List<IngredientLines> ingredientLines;


    @Override
    public String toString() {
        return "Recipe{" +
                "image='" + image + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", ingredientLines=" + Arrays.toString(new List[]{ingredientLines}) +
                '}';
    }
}
