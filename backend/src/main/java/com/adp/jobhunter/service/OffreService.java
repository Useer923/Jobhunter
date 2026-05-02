package com.adp.jobhunter.service;

import com.adp.jobhunter.entity.Offre;
import com.adp.jobhunter.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE — OffreService.java
 *
 * C'est la couche logique métier — elle fait le lien entre
 * le Controller (HTTP) et le Repository (base de données).
 *
 * Elle ne sait pas d'où vient la requête HTTP.
 * Elle ne sait pas comment les données sont stockées.
 * Elle sait juste QUOI faire avec les données.
 *
 * Equivalent de la couche services/ dans FastAPI.
 */
@Service
public class OffreService {

    @Autowired  // Spring injecte automatiquement le Repository
    private OffreRepository offreRepository;
    // Equivalent du "db: Session = Depends(get_db)" en FastAPI

    // Récupérer toutes les offres
    public List<Offre> getAll() {
        return offreRepository.findAll();
        // → SELECT * FROM offres
    }

    // Récupérer une offre par son id
    public Optional<Offre> getById(Long id) {
        return offreRepository.findById(id);
        // → SELECT * FROM offres WHERE id = ?
    }

    // Créer ou mettre à jour une offre
    public Offre save(Offre offre) {
        return offreRepository.save(offre);
        // → INSERT ou UPDATE automatiquement
    }

    // Supprimer une offre
    public void delete(Long id) {
        offreRepository.deleteById(id);
        // → DELETE FROM offres WHERE id = ?
    }

    // Rechercher par entreprise
    public List<Offre> getByEntreprise(String entreprise) {
        return offreRepository.findByEntreprise(entreprise);
        // → SELECT * FROM offres WHERE entreprise = ?
    }

    // Rechercher par mot clé dans le titre
    public List<Offre> search(String keyword) {
        return offreRepository.findByTitreContainingIgnoreCase(keyword);
        // → SELECT * FROM offres WHERE LOWER(titre) LIKE LOWER('%keyword%')
    }

    // Rechercher par niveau
    public List<Offre> getByNiveau(String niveau) {
        return offreRepository.findByNiveau(niveau);
        // → SELECT * FROM offres WHERE niveau = ?
    }

    // Rechercher par catégorie
    public List<Offre> getByCategorie(String categorie) {
        return offreRepository.findByCategorie(categorie);
        // → SELECT * FROM offres WHERE categorie = ?
    }
}