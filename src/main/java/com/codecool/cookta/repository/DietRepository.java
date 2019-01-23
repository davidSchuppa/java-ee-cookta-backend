package com.codecool.cookta.repository;

import com.codecool.cookta.model.intolerance.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
