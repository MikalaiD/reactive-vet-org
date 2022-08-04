package com.vet.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VetController {

    @GetMapping("/vet/{name}")
    public String vet(@PathVariable final String name){
        return "test";
    }
}
