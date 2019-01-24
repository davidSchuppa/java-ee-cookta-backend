package com.codecool.cookta.repository;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.intolerance.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooktaUserRepository extends JpaRepository<CooktaUser ,Long> {

    boolean existsCooktaUserByUsername(String username);

    boolean existsCooktaUserByEmail(String email);

    CooktaUser findCooktaUserByUsername(String username);


}
