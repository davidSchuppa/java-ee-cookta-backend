package com.codecool.cookta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping
    public void handleEmployeeReportRequest (
            @RequestParam String q, @RequestParam String diet, @RequestParam String health) {
        System.out.println(q);
        System.out.println(diet);
        System.out.println(health);
    }

}
