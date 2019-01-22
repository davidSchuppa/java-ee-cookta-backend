package com.codecool.cookta.repository;

import com.codecool.cookta.model.recipe.IngredientLines;
import com.codecool.cookta.model.recipe.RecipeDb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Transactional
    public void SaveRecipe() {
        IngredientLines ingredientLine = IngredientLines.builder()
                .ingredient("first ingredient line")
                .build();

        RecipeDb recipe = RecipeDb.builder()
                .image("image_path")
                .label("chicken")
                .ingredientLine(ingredientLine)
                .url("url_for_description")
                .build();

        recipeRepository.save(recipe);

        List<RecipeDb> recipeDbList = recipeRepository.findAll();

        assertThat(recipeDbList)
                .hasSize(1)
                .containsOnly(recipe);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cantSaveWithoutLabel() {
        IngredientLines line = IngredientLines.builder()
                .ingredient("first ingredient line")
                .build();

        RecipeDb recipe = RecipeDb.builder()
                .image("image_path")
                .ingredientLine(line)
                .url("url_for_description")
                .build();

        recipeRepository.save(recipe);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cantSaveWithoutIngredients() {
        RecipeDb recipe = RecipeDb.builder()
                .label("label")
                .url("url_for_desc")
                .build();

        recipeRepository.save(recipe);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cantSaveWithoutDescription() {
        IngredientLines line = IngredientLines.builder()
                .ingredient("first ingredient line")
                .build();

        RecipeDb recipe = RecipeDb.builder()
                .label("label")
                .ingredientLine(line)
                .build();

        recipeRepository.save(recipe);
    }
}