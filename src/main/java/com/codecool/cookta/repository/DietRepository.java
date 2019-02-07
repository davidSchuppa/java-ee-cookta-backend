package com.codecool.cookta.repository;

import com.codecool.cookta.model.intolerance.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface DietRepository extends JpaRepository<Diet, Long> {

    Diet findDietByCooktaUserId(Long id);

    Diet findDietByRecipeId(Long id);

}
