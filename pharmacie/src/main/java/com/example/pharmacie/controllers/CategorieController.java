package com.example.pharmacie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pharmacie.repositories.CategorieRepository;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    private final CategorieRepository categorieRepository;

    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("categories", categorieRepository.findAll());
        return "categories";
    }
}