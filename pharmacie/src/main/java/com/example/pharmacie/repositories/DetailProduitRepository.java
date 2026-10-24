package com.example.pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pharmacie.entities.DetailProduit;

public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long> {
}