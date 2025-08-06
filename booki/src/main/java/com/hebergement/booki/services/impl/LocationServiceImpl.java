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


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private final LocationCarhosRepository locationCarhosRepository;

    @Autowired
    private HebergementCarhosRepository hebergementCarhosRepository;

    @Autowired
    private UtilisateurCarhosRepository utilisateurCarhosRepository;

    public LocationServiceImpl(LocationCarhosRepository locationCarhosRepository) {
        this.locationCarhosRepository = locationCarhosRepository;
    }

    @Override
    public LocationCarhos getLocationCarhosById(Long id) {
        return  locationCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));
    }

    @Override
    public void enregistrerLocation(LocationCarhos locationCarhos) {
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
        return hebergementCarhosRepository.findAll();
    }
}
