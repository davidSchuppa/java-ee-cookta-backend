package com.codecool.cookta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {

    private Long id;

    private String username;

    private Map<String, Boolean> diet;

    private Map<String, Boolean> health;
}
