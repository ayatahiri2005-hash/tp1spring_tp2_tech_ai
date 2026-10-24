package com.example.pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pharmacie.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}