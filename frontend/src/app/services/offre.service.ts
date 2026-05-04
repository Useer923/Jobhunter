/**
 * SERVICE — offre.service.ts
 *
 * C'est la couche qui appelle l'API Spring Boot.
 * Equivalent de la couche services/ en FastAPI.
 *
 * HttpClient = requests en Python
 * Observable = async/await en Python
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Offre } from '../models/offre.model';

@Injectable({
  providedIn: 'root'  // disponible partout dans l'app
})
export class OffreService {

  // URL de base de ton API Spring Boot
  private apiUrl = 'http://localhost:8080/api/offres';

  constructor(private http: HttpClient) {}
  // HttpClient injecté automatiquement — comme Depends() en FastAPI

  // GET /api/offres → toutes les offres
  getAll(): Observable<Offre[]> {
    return this.http.get<Offre[]>(this.apiUrl);
  }

  // GET /api/offres/{id} → une offre
  getById(id: number): Observable<Offre> {
    return this.http.get<Offre>(`${this.apiUrl}/${id}`);
  }

  // POST /api/offres → créer une offre
  create(offre: Offre): Observable<Offre> {
    return this.http.post<Offre>(this.apiUrl, offre);
  }

  // GET /api/offres/search?keyword= → rechercher
  search(keyword: string): Observable<Offre[]> {
    return this.http.get<Offre[]>(`${this.apiUrl}/search?keyword=${keyword}`);
  }
}