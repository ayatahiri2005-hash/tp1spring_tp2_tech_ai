package com.example.pharmacie.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;

    @OneToOne(mappedBy = "detailProduit")
    @JsonIgnore
    private Produit produit;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFabricant() { return fabricant; }
    public void setFabricant(String fabricant) { this.fabricant = fabricant; }

    public String getPaysOrigine() { return paysOrigine; }
    public void setPaysOrigine(String paysOrigine) { this.paysOrigine = paysOrigine; }

    public String getComposition() { return composition; }
    public void setComposition(String composition) { this.composition = composition; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getIndications() { return indications; }
    public void setIndications(String indications) { this.indications = indications; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
}
