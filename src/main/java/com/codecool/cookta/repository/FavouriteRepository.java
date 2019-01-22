package com.codecool.cookta.repository;

import com.codecool.cookta.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
}
