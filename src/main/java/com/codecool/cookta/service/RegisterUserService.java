package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.repository.CooktaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserService {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean registerUser(CooktaUser cooktaUser) {
        if (!cooktaUserRepository.existsCooktaUserByUsername(cooktaUser.getUsername())
                &&
                !cooktaUserRepository.existsCooktaUserByEmail(cooktaUser.getEmail())) {
            cooktaUser.setPassword(passwordEncoder.encode(cooktaUser.getPassword()));
            Diet defaultDiet = Diet.builder().build();
            Health defaultHealth = Health.builder().build();
            cooktaUser.setDiet(defaultDiet);
            cooktaUser.setHealth(defaultHealth);
            cooktaUserRepository.save(cooktaUser);
            return true;
        }
        return false;

    }

}
