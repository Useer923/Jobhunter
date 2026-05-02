package com.adp.jobhunter.controller;

import com.adp.jobhunter.entity.Offre;
import com.adp.jobhunter.service.OffreService;
import com.adp.jobhunter.service.MuseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER — OffreController.java
 *
 * C'est la porte d'entrée HTTP de l'API.
 * Il reçoit les requêtes, délègue au Service, et renvoie la réponse.
 *
 * Equivalent du router FastAPI :
 * @app.get("/offres") → @GetMapping("/offres")
 * @app.post("/offres") → @PostMapping("/offres")
 *
 * @RestController = @Controller + @ResponseBody
 * → toutes les réponses sont automatiquement converties en JSON
 *
 * @CrossOrigin → autorise Angular (port 4200) à appeler
 * cette API (port 8080) — sinon le navigateur bloque
 */
@RestController
@RequestMapping("/api/offres")
@CrossOrigin(origins = "http://localhost:4200")
public class OffreController {

    @Autowired
    private OffreService offreService;

    @Autowired
    private MuseService museService;

    /**
     * GET /api/offres
     * Retourne toutes les offres
     * Equivalent : @app.get("/offres")
     */
    @GetMapping
    public List<Offre> getAll() {
        return offreService.getAll();
    }

    /**
     * GET /api/offres/{id}
     * Retourne une offre par son id
     * Equivalent : @app.get("/offres/{id}")
     */
    @GetMapping("/{id}")
    public ResponseEntity<Offre> getById(@PathVariable Long id) {
        return offreService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        // Si trouvé → 200 OK + l'offre en JSON
        // Si pas trouvé → 404 Not Found
    }

    /**
     * POST /api/offres
     * Crée une nouvelle offre
     * Equivalent : @app.post("/offres")
     */
    @PostMapping
    public ResponseEntity<Offre> create(@RequestBody Offre offre) {
        Offre saved = offreService.save(offre);
        return ResponseEntity.ok(saved);
        // → 200 OK + l'offre créée en JSON
    }

    /**
     * PUT /api/offres/{id}
     * Met à jour une offre existante
     * Equivalent : @app.put("/offres/{id}")
     */
    @PutMapping("/{id}")
    public ResponseEntity<Offre> update(
            @PathVariable Long id,
            @RequestBody Offre offre) {
        return offreService.getById(id)
                .map(existing -> {
                    offre.setId(id);
                    return ResponseEntity.ok(offreService.save(offre));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/offres/{id}
     * Supprime une offre
     * Equivalent : @app.delete("/offres/{id}")
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return offreService.getById(id)
                .map(offre -> {
                    offreService.delete(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/offres/search?keyword=java
     * Recherche par mot clé dans le titre
     */
    @GetMapping("/search")
    public List<Offre> search(@RequestParam String keyword) {
        return offreService.search(keyword);
        // → SELECT * FROM offres WHERE LOWER(titre) LIKE '%java%'
    }

    /**
     * POST /api/offres/fetch
     * Déclenche la récupération des offres depuis The Muse API
     * et les stocke en base H2
     *
     * Equivalent Python :
     * @app.post("/offres/fetch")
     * def fetch_from_muse():
     *     offres = muse_service.fetch_and_save()
     *     return {"message": f"{len(offres)} offres importées"}
     */
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchFromMuse() {
        List<Offre> offres = museService.fetchAndSaveOffres();
        return ResponseEntity.ok(
                offres.size() + " offres importées depuis The Muse API"
        );
    }

    @GetMapping("/niveau/{niveau}")
    public List<Offre> getByNiveau(@PathVariable String niveau) {
        return offreService.getByNiveau(niveau);
    }

    @GetMapping("/categorie/{categorie}")
    public List<Offre> getByCategorie(@PathVariable String categorie) {
        return offreService.getByCategorie(categorie);
    }
}