package com.example.pharmacie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pharmacie.repositories.FournisseurRepository;

@Controller
@RequestMapping("/fournisseurs")
public class FournisseurController {

    private final FournisseurRepository fournisseurRepository;

    public FournisseurController(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "fournisseurs";
    }
}