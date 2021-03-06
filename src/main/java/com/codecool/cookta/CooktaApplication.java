package com.codecool.cookta;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.codecool.cookta")
@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:auth0.properties")
})
public class CooktaApplication {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CooktaUserRepository cooktaUserRepository;


    public static void main(String[] args) {
        SpringApplication.run(CooktaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init(){
        return args -> {
            Diet diet = Diet.builder()
                    .balanced(true)
                    .lowCarb(true)
                    .build();

            Health health = Health.builder()
                    .gluten(true)
                    .fish(true)
                    .shellfish(true)
                    .treeNut(true)
                    .build();

            RecipeDb chicken = RecipeDb.builder()
                    .label("Csirke")
                    .image("image")
                    .url("url.com")
                    .ingredientLines(Arrays.asList("First ingredient", "Second ingredient"))
                    .build();

            RecipeDb beef = RecipeDb.builder()
                    .label("Beef")
                    .image("image")
                    .url("url.com")
                    .ingredientLines(Arrays.asList("First ingredient", "Second ingredient", "Third ingredient"))
                    .build();


            CooktaUser gabor = CooktaUser.builder()
                    .username("gabor950601")
                    .diet(diet)
                    .health(health)
                    .favourite(chicken)
                    .favourite(beef)
                    .build();


            diet.setCooktaUser(gabor);
            health.setCooktaUser(gabor);

            recipeRepository.saveAll(Arrays.asList(chicken,beef));
            cooktaUserRepository.save(gabor);
        };
    }
}


