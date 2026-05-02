package com.adp.jobhunter.repository;

import com.adp.jobhunter.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY — OffreRepository.java
 *
 * C'est la couche d'accès à la base de données.
 * Equivalent de la session SQLAlchemy en Python.
 *
 * En étendant JpaRepository, Spring génère automatiquement
 * toutes les opérations CRUD sans écrire une seule ligne de SQL.
 *
 * JpaRepository<Offre, Long> signifie :
 * - Offre = l'entité sur laquelle on travaille
 * - Long  = le type de la clé primaire (notre id)
 */
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

    // Spring génère automatiquement ces méthodes CRUD :
    // save(offre)        → INSERT ou UPDATE
    // findById(id)       → SELECT WHERE id = ?
    // findAll()          → SELECT * FROM offres
    // deleteById(id)     → DELETE WHERE id = ?
    // count()            → SELECT COUNT(*)

    // Méthode custom — Spring comprend le nom et génère le SQL tout seul
    List<Offre> findByEntreprise(String entreprise);
    // → SELECT * FROM offres WHERE entreprise = ?

    List<Offre> findByTitreContainingIgnoreCase(String keyword);
    // → SELECT * FROM offres WHERE LOWER(titre) LIKE LOWER('%keyword%')

    List<Offre> findByNiveau(String niveau);
    List<Offre> findByCategorie(String categorie);
}