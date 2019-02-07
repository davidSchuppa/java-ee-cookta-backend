package com.codecool.cookta.model.recipe;

import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
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

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String url;


    @Singular
    @ElementCollection
    @Column(nullable = false)
    private List<String> ingredientLines;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Diet recipeDiet;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Health recipeHealth;


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
