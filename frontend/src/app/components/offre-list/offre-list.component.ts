/**
 * COMPONENT — offre-list.component.ts
 *
 * C'est le cerveau du composant — il gère la logique.
 * Equivalent d'un script Python qui récupère et traite des données.
 *
 * ngOnInit() = def __init__() en Python
 * → s'exécute automatiquement au chargement du composant
 */
import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OffreService } from '../../services/offre.service';
import { Offre } from '../../models/offre.model';

@Component({
  selector: 'app-offre-list',      // balise HTML custom → <app-offre-list>
  standalone: true,                 // composant autonome — pas besoin de module
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './offre-list.component.html'
})
export class OffreListComponent implements OnInit {

  offres: Offre[] = [];            // liste des offres — vide au départ
  filteredOffres: Offre[] = [];    // liste filtrée pour la recherche
  keyword: string = '';            // mot clé de recherche
  isLoading: boolean = false;      // état de chargement
  errorMessage: string = '';       // message d'erreur

  constructor(private offreService: OffreService) {}
  // OffreService injecté automatiquement — comme Depends() en FastAPI

  // S'exécute automatiquement au chargement du composant
  ngOnInit(): void {
    this.loadOffres();
  }

  // Charge toutes les offres depuis l'API
  loadOffres(): void {
    this.isLoading = true;
    this.offreService.getAll().subscribe({
      next: (data) => {
        // next = succès — équivalent du "then" en Promise
        this.offres = data;
        this.filteredOffres = data;
        this.isLoading = false;
      },
      error: (err) => {
        // error = échec — équivalent du "catch" en Python
        this.errorMessage = 'Erreur lors du chargement des offres';
        this.isLoading = false;
      }
    });
  }

  // Filtre les offres par mot clé — en local, pas d'appel API
  filterOffres(): void {
    if (!this.keyword.trim()) {
      this.filteredOffres = this.offres;
      return;
    }
    this.filteredOffres = this.offres.filter(offre =>
      offre.titre.toLowerCase().includes(this.keyword.toLowerCase()) ||
      offre.entreprise.toLowerCase().includes(this.keyword.toLowerCase())
    );
  }
}