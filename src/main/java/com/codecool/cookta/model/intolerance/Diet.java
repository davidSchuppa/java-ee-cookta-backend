package com.codecool.cookta.model.intolerance;


import com.codecool.cookta.model.CooktaUser;
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
public class Diet {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "diet")
    private CooktaUser cooktaUser;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean vegetarian;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean paleo;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean lowFat;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean lowCarb;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean lowSodium;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean balanced;

    public Map<String, Boolean> getDietFields() throws IllegalAccessException {
        Map<String, Boolean> dietFields = new HashMap<>();

        for(Field field : Diet.class.getDeclaredFields()){
            field.setAccessible(true);
            if(!field.getName().equals("id") && !field.getName().equals("cooktaUser")) {
                dietFields.put(field.getName(), (Boolean) field.get(this));
            }
        }
        return dietFields;
    }

    public void updateFields(Map<String, Boolean> userData) throws IllegalAccessException {

        for(Field field : Diet.class.getDeclaredFields()){
            field.setAccessible(true);
            for(String key : userData.keySet()){
                if(field.getName().toLowerCase().equals(key)){
                    field.set(this, userData.get(key));
                }
            }
        }
    }
}
