package com.codecool.cookta.model;


import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CooktaUser {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    @Singular
    @ElementCollection
    // unique name
    private List<RecipeDb> favourites;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Diet diet;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Health health;
}
