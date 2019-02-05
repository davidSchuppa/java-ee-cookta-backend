package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.repository.CooktaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginValidation {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginData validation(Map<String, String> data) throws IllegalAccessException {
        try {
            CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(data.get("username"));
            LoginData loginData = new LoginData(user.getId(), user.getUsername(), user.getDiet().getDietFields(), user.getHealth().getHealthFields());
            if (encoder.matches(data.get("password"), user.getPassword())) {
                return loginData;
            } else {
                return null;
            }
        }catch (NullPointerException e){
            return null;
        }

    }
}
