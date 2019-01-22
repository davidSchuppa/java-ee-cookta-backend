package com.codecool.cookta.repository;

import com.codecool.cookta.model.CooktaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooktaUserRepository extends JpaRepository<CooktaUser ,Long> {
}
