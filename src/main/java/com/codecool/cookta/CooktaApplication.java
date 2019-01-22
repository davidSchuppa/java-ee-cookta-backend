package com.codecool.cookta;

import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CooktaApplication {

    @Autowired
    private RecipeRepository recipeRepository;

    public static void main(String[] args) {
        SpringApplication.run(CooktaApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {
            Recipe lel = Recipe.builder()
                    .label("LEL")
                    .build();


            recipeRepository.save(lel);
        };
    }
}


