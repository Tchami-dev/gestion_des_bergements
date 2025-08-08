package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.HebergementCarhos;
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
    private HebergementCarhosRepository hebergementCarhosRepository;

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

        // 2. Normaliser l'email (supprimer espaces et mettre en minuscule)
        String emailClient = locationCarhos.getUtilisateurCarhos()
                .getEmailUtilisateur()
                .trim()
                .toLowerCase();

        // 3. Recherche de l'utilisateur existant
        Optional<UtilisateurCarhos> utilisateurOpt =
                utilisateurCarhosRepository.findByEmailUtilisateur(emailClient);

        UtilisateurCarhos utilisateurCarhos;

        if (utilisateurOpt.isPresent()) {
            // Utilisateur déjà existant → on rattache la location à cet utilisateur
            utilisateurCarhos = utilisateurOpt.get();
        } else {
            // Nouvel utilisateur → on met à jour l'email et on enregistre
            locationCarhos.getUtilisateurCarhos().setEmailUtilisateur(emailClient);
            utilisateurCarhos = utilisateurCarhosRepository.save(locationCarhos.getUtilisateurCarhos());
        }

        // 4. Associer l'utilisateur à la location
        locationCarhos.setUtilisateurCarhos(utilisateurCarhos);

        // 5. Sauvegarder la location
        locationCarhosRepository.save(locationCarhos);
    }



    @Override
    public void supprimerLocation(Long id) {
        LocationCarhos locationCarhos = locationCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("location introuvable avec l'ID : " + id));
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
