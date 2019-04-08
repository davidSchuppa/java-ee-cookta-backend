package com.codecool.cookta.model;


import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CooktaUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Singular
    @ElementCollection
    // unique name
    private List<RecipeDb> favourites;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Diet diet;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Health health;

    public void appendFavourite(RecipeDb recipe) {
        if (!favourites.contains(recipe)) {
            favourites.add(recipe);
        }
    }

    public void removeFavourite(RecipeDb recipe) {
        favourites.remove(recipe);
    }
}
