package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.LocationCarhos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    LocationCarhos getLocationCarhosById(Long id);

    void enregistrerLocation(LocationCarhos locationCarhos);

    void supprimerLocation (Long id);

    Page<LocationCarhos> getPageLocation(Pageable page);

    List<HebergementCarhos> getAllHebergement();
}
