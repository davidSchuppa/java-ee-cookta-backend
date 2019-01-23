package com.codecool.cookta.model.recipe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IngredientLines {

    @Id
    @GeneratedValue
    private Long id;

    private String ingredient;

    @ManyToOne
    private RecipeDb recipe;

}
