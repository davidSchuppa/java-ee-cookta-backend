package com.codecool.cookta.service;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.LoginData;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
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
        LoginData loginData;
        try {
            CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(data.get("username"));
            loginData = new LoginData(user.getId(),
                    user.getUsername(),
                    user.getDiet().getDietFields(),
                    user.getHealth().getHealthFields());

        } catch (NullPointerException e){
            CooktaUser newUser = CooktaUser.builder()
                    .username(data.get("username"))
                    .diet(Diet.builder().build())
                    .health(Health.builder().build())
                    .build();

            cooktaUserRepository.save(newUser);
            CooktaUser user = cooktaUserRepository.findCooktaUserByUsername(data.get("username"));
            loginData = new LoginData(user.getId(),
                    user.getUsername(),
                    user.getDiet().getDietFields(),
                    user.getHealth().getHealthFields());
        }
        return loginData;

    }
}
