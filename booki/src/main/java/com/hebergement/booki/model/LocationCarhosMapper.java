package com.hebergement.booki.model;


import com.hebergement.booki.services.inter.HebergementService;
import com.hebergement.booki.services.inter.UtilisateurService;
import org.springframework.stereotype.Component;

@Component
public class LocationCarhosMapper {

    private final HebergementService hebergementService;



    public LocationCarhosMapper(HebergementService hebergementService, UtilisateurService utilisateurService) {
        this.hebergementService = hebergementService;
    }

    public LocationCarhos toEntity(LocationCarhosDTO locationCarhosDto){

        LocationCarhos locationCarhos = new LocationCarhos();

        if (locationCarhosDto.getHebergementId() != null) {
            locationCarhos.setHebergementCarhos(
                    hebergementService.getHebergementCarhosById(locationCarhosDto.getHebergementId())
//                    hebergementService.getHebergementCarhosById(locationCarhosDto.getHebergementNom())
            );
        }

        return locationCarhos;
    }

    public LocationCarhosDTO toDTO(LocationCarhos entity) {
        LocationCarhosDTO locationCarhosDto = new LocationCarhosDTO();
        locationCarhosDto.setHebergementId(entity.getHebergementCarhos().getId());

        return locationCarhosDto;
    }

}
