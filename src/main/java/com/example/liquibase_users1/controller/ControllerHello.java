package com.example.liquibase_users1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ControllerHello {


    @GetMapping
    public String Hello() {
        return "Hello";
    }

}

