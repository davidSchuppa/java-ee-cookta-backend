package com.codecool.cookta.model;


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
    @OneToMany(mappedBy = "cooktaUserId", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Favourite> favourites;
}
