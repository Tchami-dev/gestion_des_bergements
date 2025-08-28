package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.LocationCarhos;
import com.hebergement.booki.model.LocationCarhosDTO;
import com.hebergement.booki.model.UtilisateurCarhos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    LocationCarhos getLocationCarhosById(Long id);

    void enregistrerLocation(LocationCarhos locationCarhos);

    void supprimerLocation (Long id);

    Page<LocationCarhos> getPageLocation(Pageable page);

    List<HebergementCarhos> getAllHebergement();

    /*******surcharge et DTO *****/

    // Nouvelles signatures basées sur le DTO
    LocationCarhosDTO getLocationDTOById(Long id);
    void enregistrerLocation(LocationCarhosDTO locationDTO);
    Page<LocationCarhosDTO> getPageLocationDTO(Pageable page);

}
