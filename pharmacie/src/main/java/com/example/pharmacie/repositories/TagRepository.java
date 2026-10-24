package com.example.pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pharmacie.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}