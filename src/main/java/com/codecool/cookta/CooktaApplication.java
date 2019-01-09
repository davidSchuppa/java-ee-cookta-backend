package com.codecool.cookta;

import com.codecool.cookta.service.JsonMapper;
import com.codecool.cookta.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CooktaApplication {

    @Autowired
    private RequestHandler requestHandler;

    @Autowired
    private JsonMapper jsonMapper;


    public static void main(String[] args) {
        SpringApplication.run(CooktaApplication.class, args);
    }

}


