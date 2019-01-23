package com.codecool.cookta.repository;

import com.codecool.cookta.model.CooktaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CooktaUserRepository extends JpaRepository<CooktaUser ,Long> {

    CooktaUser findCooktaUserByUsername(String username);
}
