package com.adp.jobhunter;

import com.adp.jobhunter.service.MuseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * DataInitializer — s'exécute automatiquement au démarrage de Spring Boot
 * Lance le fetch des offres depuis The Muse API dès que l'app est prête
 *
 * Equivalent Python :
 * @app.on_event("startup")
 * async def startup():
 *     await muse_service.fetch_and_save()
 */
@Component
public class DataInitializer {

    @Autowired
    private MuseService museService;

    @EventListener(ApplicationReadyEvent.class)
    // S'exécute quand Spring Boot a fini de démarrer
    // Equivalent FastAPI : @app.on_event("startup")
    public void fetchOffresOnStartup() {
        System.out.println("🚀 Démarrage du fetch automatique depuis The Muse API...");
        var offres = museService.fetchAndSaveOffres();
        System.out.println("✅ " + offres.size() + " offres importées automatiquement !");
    }
}