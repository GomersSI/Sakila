package com.example.sakila.controllers;

import com.example.sakila.entities.Category;
import com.example.sakila.repositories.CategoryRepository;
import com.example.sakila.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping("/categories")
    public List<Category> listCategories(@RequestParam(required = false) Map<String, String> params){
        return categoryService.getCategory(params);
    }

    @GetMapping("/categories/{id}")
    private Category getCategoryById(@PathVariable Short id){
        return categoryService.getCategoryById(id);
    }
}
