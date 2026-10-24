package com.example.pharmacie.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacie.entities.Categorie;
import com.example.pharmacie.entities.DetailProduit;
import com.example.pharmacie.entities.Fournisseur;
import com.example.pharmacie.entities.Produit;
import com.example.pharmacie.entities.Tag;
import com.example.pharmacie.repositories.CategorieRepository;
import com.example.pharmacie.repositories.FournisseurRepository;
import com.example.pharmacie.repositories.ProduitRepository;
import com.example.pharmacie.repositories.TagRepository;

@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final FournisseurRepository fournisseurRepository;
    private final TagRepository tagRepository;

    public ProduitRestController(ProduitRepository produitRepository,
                                 CategorieRepository categorieRepository,
                                 FournisseurRepository fournisseurRepository,
                                 TagRepository tagRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Produit produit) {
        produit.setId(null);

        String erreur = resoudreRelations(produit);
        if (erreur != null) {
            return ResponseEntity.badRequest().body(erreur);
        }

        Produit saved = produitRepository.save(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Produit data) {
        Produit existing = produitRepository.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        String erreur = resoudreRelations(data);
        if (erreur != null) {
            return ResponseEntity.badRequest().body(erreur);
        }

        existing.setNom(data.getNom());
        existing.setDescription(data.getDescription());
        existing.setPrix(data.getPrix());
        existing.setCodeBarre(data.getCodeBarre());
        existing.setDateExpiration(data.getDateExpiration());
        existing.setCategorie(data.getCategorie());
        existing.setFournisseur(data.getFournisseur());
        existing.setTags(data.getTags());

        // On modifie le détail existant au lieu d'en créer un nouveau
        DetailProduit nouveau = data.getDetailProduit();
        if (nouveau != null) {
            DetailProduit detail = existing.getDetailProduit() != null
                    ? existing.getDetailProduit()
                    : new DetailProduit();
            detail.setFabricant(nouveau.getFabricant());
            detail.setPaysOrigine(nouveau.getPaysOrigine());
            detail.setComposition(nouveau.getComposition());
            detail.setDosage(nouveau.getDosage());
            detail.setIndications(nouveau.getIndications());
            existing.setDetailProduit(detail);
        }

        return ResponseEntity.ok(produitRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!produitRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remplace les objets "{ id: x }" reçus dans le JSON par les vraies entités
     * chargées depuis la base. Retourne un message d'erreur, ou null si tout va bien.
     */
    private String resoudreRelations(Produit produit) {
        if (produit.getCategorie() != null) {
            Long catId = produit.getCategorie().getId();
            if (catId == null) {
                return "categorie.id manquant";
            }
            Categorie categorie = categorieRepository.findById(catId).orElse(null);
            if (categorie == null) {
                return "Catégorie introuvable : id " + catId;
            }
            produit.setCategorie(categorie);
        }

        if (produit.getFournisseur() != null) {
            Long fourId = produit.getFournisseur().getId();
            if (fourId == null) {
                return "fournisseur.id manquant";
            }
            Fournisseur fournisseur = fournisseurRepository.findById(fourId).orElse(null);
            if (fournisseur == null) {
                return "Fournisseur introuvable : id " + fourId;
            }
            produit.setFournisseur(fournisseur);
        }

        List<Tag> tags = new ArrayList<>();
        if (produit.getTags() != null) {
            for (Tag t : produit.getTags()) {
                if (t.getId() == null) {
                    return "tag.id manquant";
                }
                Tag tag = tagRepository.findById(t.getId()).orElse(null);
                if (tag == null) {
                    return "Tag introuvable : id " + t.getId();
                }
                tags.add(tag);
            }
        }
        produit.setTags(tags);

        return null;
    }
}