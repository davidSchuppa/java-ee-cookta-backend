package com.codecool.cookta.model;


import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import lombok.*;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "cooktaUser", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Favourite> favourites;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Diet diet;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Health health;
}
