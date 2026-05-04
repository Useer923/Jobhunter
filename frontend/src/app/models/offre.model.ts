/**
 * MODELE — offre.model.ts
 *
 * C'est l'équivalent du schéma Pydantic en Python.
 * Il définit la structure d'une offre côté Angular.
 * Doit correspondre exactement à l'entité Java Offre.java
 */
export interface Offre {
  id?: number;
  titre: string;
  entreprise: string;
  localisation: string;
  description: string;
  niveau: string;
  categorie: string;
  lienOffre: string;
  datePublication: string;
}