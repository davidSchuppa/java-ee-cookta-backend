package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.repository.CooktaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserService {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;


    public void registerUser(CooktaUser cooktaUser) {
        cooktaUserRepository.save(cooktaUser);
    }

}
