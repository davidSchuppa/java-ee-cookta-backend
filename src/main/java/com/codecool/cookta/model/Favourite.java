package com.codecool.cookta.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Favourite {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Long cooktaUserId;

    @ManyToOne
    private Long recipeId;
}
