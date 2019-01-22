package com.codecool.cookta;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.Favourite;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.intolerance.Diet;
import com.codecool.cookta.model.intolerance.Health;
import com.codecool.cookta.model.recipe.IngredientLines;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CooktaApplication {

    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private CooktaUserRepository cooktaUserRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private HealthRepository healthRepository;

    @Autowired
    private DietRepository dietRepository;

    public static void main(String[] args) {
        SpringApplication.run(CooktaApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {
            Diet diet = Diet.builder()
                    .build();

            Health health = Health.builder()
                    .build();

            IngredientLines first_ingerdient = IngredientLines.builder()
                    .ingredient("First ingerdient")
                    .build();

            IngredientLines second_ingerdient = IngredientLines.builder()
                    .ingredient("Second ingerdient")
                    .build();

            CooktaUser gabor = CooktaUser.builder()
                    .username("gabor")
                    .password("pasword")
                    .email("email@email.com")
                    .diet(diet)
                    .health(health)
                    .build();

            RecipeDb chicken = RecipeDb.builder()
                    .label("Csirke")
                    .image("image")
                    .url("url.com")
                    .ingredientLine(first_ingerdient)
                    .ingredientLine(second_ingerdient)
                    .build();

            first_ingerdient.setRecipe(chicken);
            second_ingerdient.setRecipe(chicken);

            Favourite gaborFav = Favourite.builder()
                    .cooktaUser(gabor)
                    .recipe(chicken)
                    .build();

            gaborFav.setCooktaUser(gabor);
            gaborFav.setRecipe(chicken);
            diet.setCooktaUser(gabor);
            health.setCooktaUser(gabor);

            cooktaUserRepository.save(gabor);
            healthRepository.save(health);
            dietRepository.save(diet);
            recipeRepository.save(chicken);
            favouriteRepository.save(gaborFav);
        };
    }
}


