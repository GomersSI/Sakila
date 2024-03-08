package com.example.sakila.controllers;

import com.example.sakila.entities.Language;
import com.example.sakila.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
public class LanguageController {

    @Autowired
    LanguageRepository languageRepository;

    @GetMapping("/languages")
    public List<Language> listLanguages(){
        return languageRepository.findAll();
    }

    @GetMapping("/languages/{id}")
    public Language getLanguageById(@PathVariable Short id){
        return languageRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such film."));
    }
}
