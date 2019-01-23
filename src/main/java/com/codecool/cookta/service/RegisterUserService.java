package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.repository.CooktaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserService {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;


    public boolean registerUser(CooktaUser cooktaUser) {
        if (!cooktaUserRepository.existsCooktaUserByUsername(cooktaUser.getUsername())
                &&
                !cooktaUserRepository.existsCooktaUserByEmail(cooktaUser.getEmail())) {
            cooktaUserRepository.save(cooktaUser);
            return true;
        }
        return false;

    }

}
