package com.codecool.cookta.model;


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
public class CooktaUser {

    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "cooktaUserId", cascade = CascadeType.PERSIST)
    private Long id;

    private String username;

    private String password;

    private String email;
}
