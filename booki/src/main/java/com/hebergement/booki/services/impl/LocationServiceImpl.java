package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosStatut;
import com.hebergement.booki.model.LocationCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.repository.LocationCarhosRepository;
import com.hebergement.booki.repository.UtilisateurCarhosRepository;
import com.hebergement.booki.services.inter.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private final LocationCarhosRepository locationCarhosRepository;

    @Autowired
    private final UtilisateurCarhosRepository utilisateurCarhosRepository;

    @Autowired
    private final HebergementCarhosRepository hebergementCarhosRepository;

    public LocationServiceImpl(LocationCarhosRepository locationCarhosRepository,
                               UtilisateurCarhosRepository utilisateurCarhosRepository,
                               HebergementCarhosRepository hebergementCarhosRepository) {
        this.locationCarhosRepository = locationCarhosRepository;
        this.utilisateurCarhosRepository = utilisateurCarhosRepository;
        this.hebergementCarhosRepository = hebergementCarhosRepository;
    }

    @Override
    public LocationCarhos getLocationCarhosById(Long id) {
        return  locationCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));
    }



    @Override
    public void enregistrerLocation(LocationCarhos locationCarhos) {
        // 1. Vérifier que l'utilisateur et son email sont bien fournis
        if (locationCarhos.getUtilisateurCarhos() == null ||
                locationCarhos.getUtilisateurCarhos().getEmailUtilisateur() == null ||
                locationCarhos.getUtilisateurCarhos().getEmailUtilisateur().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "L'email de l'utilisateur est obligatoire pour créer une location."
            );
        }

        // 2. Normaliser l'email
        String emailClient = locationCarhos.getUtilisateurCarhos()
                .getEmailUtilisateur()
                .trim()
                .toLowerCase();

        // 3. Recherche de l'utilisateur existant
        Optional<UtilisateurCarhos> utilisateurOpt =
                utilisateurCarhosRepository.findByEmailUtilisateur(emailClient);

        UtilisateurCarhos utilisateurCarhos;

        if (utilisateurOpt.isPresent()) {
            utilisateurCarhos = utilisateurOpt.get();
        } else {
            locationCarhos.getUtilisateurCarhos().setEmailUtilisateur(emailClient);
            utilisateurCarhos = utilisateurCarhosRepository.save(locationCarhos.getUtilisateurCarhos());
        }

        // 4. Associer l'utilisateur à la location
        locationCarhos.setUtilisateurCarhos(utilisateurCarhos);

        // 5. Récupérer l'hébergement complet depuis la base pour vérifier son état
        HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(locationCarhos.getHebergementCarhos().getId())
                .orElseThrow(() -> new RuntimeException("Hébergement introuvable"));

        // 6. Vérifier que l'hébergement est libre avant réservation
        if (hebergementCarhos.getEtatHebergement() == HebergementCarhosStatut.OCCUPE) {
            throw new IllegalStateException("Cet hébergement est déjà occupé.");
        }

        // 7. Mettre à jour l'état de l'hébergement à OCCUPE
        hebergementCarhos.setEtatHebergement(HebergementCarhosStatut.OCCUPE);
        hebergementCarhosRepository.save(hebergementCarhos);

        // 8. Associer l'hébergement mis à jour à la location
        locationCarhos.setHebergementCarhos(hebergementCarhos);

        // 9. Sauvegarder la location
        locationCarhosRepository.save(locationCarhos);
    }



    @Override
    public void supprimerLocation(Long id) {
        LocationCarhos locationCarhos = locationCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("location introuvable avec l'ID : " + id));

        HebergementCarhos hebergementCarhos = locationCarhos.getHebergementCarhos();
        if (hebergementCarhos != null) {
            hebergementCarhos.setEtatHebergement(HebergementCarhosStatut.LIBRE);
            hebergementCarhosRepository.save(hebergementCarhos);
        }

        locationCarhosRepository.delete(locationCarhos);
    }

    @Override
    public Page<LocationCarhos> getPageLocation(Pageable page) {
        return locationCarhosRepository.findAll(page);
    }

    @Override
    public List<HebergementCarhos> getAllHebergement() {
        return hebergementCarhosRepository.findAll()
                /*.stream().map(HebergementCarhos::getNom)
                .collect(Collectors.toList())*/ ; //pour obtenir uniquement les noms
    }


}
