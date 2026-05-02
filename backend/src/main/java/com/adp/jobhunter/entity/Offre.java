package com.adp.jobhunter.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ENTITE JPA — Offre.java
 *
 * Ce fichier fait office de schéma Pydantic + modèle SQLAlchemy en Python.
 * Spring Boot lit cette classe au démarrage et génère automatiquement
 * la table SQL correspondante dans H2. Pas besoin de CREATE TABLE.
 *
 * Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor) génère
 * automatiquement les getters, setters et constructeurs — équivalent
 * du @dataclass Python.
 */ 

@Data                  // génère getters/setters/toString automatiquement
@NoArgsConstructor     // génère un constructeur vide
@AllArgsConstructor    // génère un constructeur avec tous les champs
@Entity                // dit à Spring que c'est une table en base
@Table(name = "offres") // nom de la table en base
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String entreprise;

    @Column
    private String localisation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String niveau;      // Entry, Mid, Senior

    @Column
    private String categorie;   // Data and Analytics, Software Engineering...

    @Column
    private String lienOffre;   // URL vers l'offre originale sur The Muse

    @Column
    private String datePublication;
}