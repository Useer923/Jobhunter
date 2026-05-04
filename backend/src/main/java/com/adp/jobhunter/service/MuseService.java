package com.adp.jobhunter.service;

import com.adp.jobhunter.entity.Offre;
import com.adp.jobhunter.repository.OffreRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MuseService {

    @Autowired
    private OffreRepository offreRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MUSE_API_URL =
            "https://www.themuse.com/api/public/jobs" +
                    "?category=Data+and+Analytics" +
                    "&page=1" +
                    "&descending=true";

    @Transactional
    public List<Offre> fetchAndSaveOffres() {
        List<Offre> offres = new ArrayList<>();

        // 1. EXTRACT — appel HTTP à The Muse API
        String response = restTemplate.getForObject(MUSE_API_URL, String.class);

        try {
            // 2. TRANSFORM — parsing du JSON
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.get("results");

            if (results != null && results.isArray()) {
                for (JsonNode job : results) {
                    Offre offre = mapToOffre(job);
                    offres.add(offre);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur parsing JSON : " + e.getMessage());
            return offres;
        }

        // 3. LOAD — stockage en base H2
        return offreRepository.saveAll(offres);
    }

    private Offre mapToOffre(JsonNode job) {
        Offre offre = new Offre();

        offre.setTitre(getTextSafely(job, "name"));

        JsonNode company = job.get("company");
        offre.setEntreprise(company != null ?
                getTextSafely(company, "name") : "Inconnu");

        JsonNode locations = job.get("locations");
        if (locations != null && locations.isArray() && locations.size() > 0) {
            offre.setLocalisation(getTextSafely(locations.get(0), "name"));
        } else {
            offre.setLocalisation("Remote");
        }

        offre.setDescription(getTextSafely(job, "contents"));

        JsonNode levels = job.get("levels");
        if (levels != null && levels.isArray() && levels.size() > 0) {
            offre.setNiveau(getTextSafely(levels.get(0), "name"));
        } else {
            offre.setNiveau("Non spécifié");
        }

        JsonNode categories = job.get("categories");
        if (categories != null && categories.isArray() && categories.size() > 0) {
            offre.setCategorie(getTextSafely(categories.get(0), "name"));
        } else {
            offre.setCategorie("Non spécifié");
        }

        JsonNode refs = job.get("refs");
        offre.setLienOffre(refs != null ?
                getTextSafely(refs, "landing_page") : "");

        offre.setDatePublication(getTextSafely(job, "publication_date"));

        return offre;
    }

    private String getTextSafely(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && !value.isNull() ? value.asText() : "";
    }
}