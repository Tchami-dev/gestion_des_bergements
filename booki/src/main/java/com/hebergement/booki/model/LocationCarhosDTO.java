package com.hebergement.booki.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocationCarhosDTO {

    //private Long idLocation;

    private  Long hebergementId;

    private String hebergementNom = getHebergementNom();

}
