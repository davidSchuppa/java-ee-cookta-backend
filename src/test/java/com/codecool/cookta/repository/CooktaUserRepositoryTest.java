package com.codecool.cookta.repository;

import com.codecool.cookta.model.CooktaUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CooktaUserRepositoryTest {

    @Autowired
    private CooktaUserRepository cooktaUserRepository;


    @Test
    @Transactional
    public void saveCooktaUser() {
        CooktaUser user = CooktaUser.builder()
                .username("admin")
                .password("password")
                .email("admin@admin.com")
                .build();

        cooktaUserRepository.save(user);

        List<CooktaUser> users = cooktaUserRepository.findAll();

        assertThat(users)
                .hasSize(1)
                .containsOnly(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void usernameCantBeNull() {
        CooktaUser user = CooktaUser.builder()
                .password("password")
                .email("admin@admin.com").
                build();

        cooktaUserRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void passwordCantBeNull() {
        CooktaUser user = CooktaUser.builder()
                .username("admin")
                .email("admin@admin.com")
                .build();

        cooktaUserRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void emailCantBeNull() {
        CooktaUser user = CooktaUser.builder()
                .username("admin")
                .password("password")
                .build();

        cooktaUserRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cantSaveSameUsernames() {
        CooktaUser user = CooktaUser.builder()
                .username("admin")
                .password("password")
                .email("admin@admin.com")
                .build();

        CooktaUser user2 = CooktaUser.builder()
                .username("admin")
                .password("passwordcanbesame")
                .email("admin2@admin.com")
                .build();

        cooktaUserRepository.save(user);
        cooktaUserRepository.saveAndFlush(user2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cantSaveSameEmails() {
        CooktaUser user = CooktaUser.builder()
                .username("admin")
                .password("password")
                .email("admin@admin.com")
                .build();

        CooktaUser user2 = CooktaUser.builder()
                .username("admin2")
                .password("password")
                .email("admin@admin.com")
                .build();

        cooktaUserRepository.save(user);
        cooktaUserRepository.saveAndFlush(user2);
    }


}