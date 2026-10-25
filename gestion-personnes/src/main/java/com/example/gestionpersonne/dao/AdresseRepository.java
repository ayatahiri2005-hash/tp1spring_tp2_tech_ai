package com.example.gestionpersonne.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionpersonne.model.Adresse;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {
}
