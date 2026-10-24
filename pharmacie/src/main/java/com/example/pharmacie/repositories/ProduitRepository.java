package com.example.pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pharmacie.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}