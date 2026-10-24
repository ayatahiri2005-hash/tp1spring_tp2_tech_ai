package com.example.pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pharmacie.entities.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}