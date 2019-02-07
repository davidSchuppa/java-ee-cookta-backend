package com.codecool.cookta.repository;

import com.codecool.cookta.model.intolerance.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {

    Health findByCooktaUserId(Long id);

    Health findByRecipeId(Long id);
}
