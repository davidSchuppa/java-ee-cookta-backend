package com.codecool.cookta.model.intolerance;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.recipe.RecipeDb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Health {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "health")
    private CooktaUser cooktaUser;

    @OneToOne(mappedBy = "recipeHealth")
    private RecipeDb recipe;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean gluten;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean soy;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean peanut;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean fish;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean dairy;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean shellfish;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean egg;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean treeNut;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean wheat;

    public Map<String, Boolean> getHealthFields() throws IllegalAccessException {
        Map<String, Boolean> dietFields = new HashMap<>();

        for(Field field : Health.class.getDeclaredFields()){
            field.setAccessible(true);
            if(!field.getName().equals("id") && !field.getName().equals("cooktaUser")) {
                dietFields.put(field.getName(), (Boolean) field.get(this));
            }
        }
        return dietFields;
    }

    public void updateFields(Map<String, Boolean> userData) throws IllegalAccessException {

        for(Field field : Health.class.getDeclaredFields()){
            field.setAccessible(true);
            for(String key : userData.keySet()){
                if(field.getName().toLowerCase().equals(key)){
                    field.set(this, userData.get(key));
                }
            }
        }
    }
}
