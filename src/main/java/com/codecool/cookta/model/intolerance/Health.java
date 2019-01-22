package com.codecool.cookta.model.intolerance;

import com.codecool.cookta.model.CooktaUser;
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
public class Health {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private CooktaUser cooktaUserId;

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
    private boolean shellFish;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean egg;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean treeNut;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean wheat;
}
