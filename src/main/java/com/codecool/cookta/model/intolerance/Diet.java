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
public class Diet {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private CooktaUser cooktaUserId;

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
}
