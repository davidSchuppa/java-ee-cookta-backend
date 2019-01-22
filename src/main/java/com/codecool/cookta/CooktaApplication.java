package com.codecool.cookta;

import com.codecool.cookta.model.CooktaUser;
import com.codecool.cookta.model.Favourite;
import com.codecool.cookta.model.dto.Recipe;
import com.codecool.cookta.model.recipe.IngredientLines;
import com.codecool.cookta.model.recipe.RecipeDb;
import com.codecool.cookta.repository.CooktaUserRepository;
import com.codecool.cookta.repository.FavouriteRepository;
import com.codecool.cookta.repository.RecipeRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(CooktaApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {

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
                    .build();


            RecipeDb chicken = RecipeDb.builder()
                    .label("Csirke")
                    .image("image")
                    .url("url.com")
                    .ingredientLine(first_ingerdient)
                    .ingredientLine(second_ingerdient)
                    .build();

            Favourite gaborFav = Favourite.builder()
                    .cooktaUser(gabor)
                    .recipe(chicken)
                    .build();

            favouriteRepository.save(gaborFav);
            cooktaUserRepository.save(gabor);
            recipeRepository.save(chicken);
        };
    }
}


