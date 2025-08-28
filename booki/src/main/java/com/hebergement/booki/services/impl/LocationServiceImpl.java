package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.*;
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


    private final LocationCarhosRepository locationCarhosRepository;

    private final LocationCarhosMapper locationCarhosMapper;

    private final UtilisateurCarhosRepository utilisateurCarhosRepository;

    private final HebergementCarhosRepository hebergementCarhosRepository;

    public LocationServiceImpl(LocationCarhosRepository locationCarhosRepository, LocationCarhosMapper locationCarhosMapper,
                               UtilisateurCarhosRepository utilisateurCarhosRepository,
                               HebergementCarhosRepository hebergementCarhosRepository) {
        this.locationCarhosRepository = locationCarhosRepository;
        this.locationCarhosMapper = locationCarhosMapper;
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
        if (hebergementCarhos.getHebergementCarhosStatut() == HebergementCarhosStatut.OCCUPE) {
            throw new IllegalStateException("Cet hébergement est déjà occupé.");
        }

        // 7. Mettre à jour l'état de l'hébergement à OCCUPE
        hebergementCarhos.setHebergementCarhosStatut(HebergementCarhosStatut.OCCUPE);
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
            hebergementCarhos.setHebergementCarhosStatut(HebergementCarhosStatut.LIBRE);
            hebergementCarhosRepository.save(hebergementCarhos);
        }

        locationCarhosRepository.delete(locationCarhos);
    }

    @Override
    public List<HebergementCarhos> getAllHebergement() {
        return hebergementCarhosRepository.findAll();
    }

    @Override
    public Page<LocationCarhos> getPageLocation(Pageable page) {
        return locationCarhosRepository.findAll(page);
    }

    /********* DTO ********/

    @Override
    public LocationCarhosDTO getLocationDTOById(Long id) {
        return locationCarhosRepository.findById(id)
        .map(locationCarhosMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Location introuvable"));
    }

    @Override
    public void enregistrerLocation(LocationCarhosDTO locationDTO) {
        LocationCarhos locationCarhos = locationCarhosMapper.toEntity(locationDTO);
        locationCarhosRepository.save(locationCarhos);
    }

    @Override
    public Page<LocationCarhosDTO> getPageLocationDTO(Pageable page) {
        return locationCarhosRepository.findAll(page)
                .map(locationCarhosMapper::toDTO);
    }

}

 /*.stream().map(HebergementCarhos::getNom)
                .collect(Collectors.toList())*/ ; //pour obtenir uniquement les noms

