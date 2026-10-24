package com.example.pharmacie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pharmacie.entities.Produit;
import com.example.pharmacie.repositories.ProduitRepository;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitRepository produitRepository;

    public ProduitController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "produits";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit == null) {
            return "redirect:/produits";
        }
        model.addAttribute("produit", produit);
        return "produit-details";
    }
}