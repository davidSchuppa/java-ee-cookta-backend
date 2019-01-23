package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.repository.CooktaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class LoginValidation {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;

    public LoginData validation(Map<String, String> data) throws IllegalAccessException {
        CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(data.get("username"));
        System.out.println(Arrays.asList(user.getDiet().getDietFields()));
        LoginData loginData = new LoginData(user.getId(), user.getUsername(), user.getDiet().getDietFields(), user.getHealth().getHealthFields());
        if (user.getPassword().equals(data.get("password"))) {
            return loginData;
        } else {
            return null;
        }
    }
}
