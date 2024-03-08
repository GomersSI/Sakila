package com.example.sakila.services;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Category;
import com.example.sakila.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategory(@RequestParam(required = false) Map<String, String> params){
        String categoryName = params.get("category");
        if (categoryName != null){
            return getCategoryByName(categoryName);
        }
        return categoryRepository.findAll();
    }

    public Category getCategoryById(@PathVariable Short id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such category."));
    }

    public List<Category> getCategoryByName(String name){
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
