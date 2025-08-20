package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HebergementService {


    HebergementCarhos getHebergementCarhosById(Long id); //obtenir un hebergement par son id

     void supprimerHebergementCarhos(Long id);     //supprimer un hebergement par son id

    void enregistrerHebergement(HebergementCarhos hebergementCarhos, MultipartFile fichierImage);    //enregister un hébergement

    Page<HebergementCarhos> getPageHebergementGauche(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableGauche);

    Page<HebergementCarhos> getPageHebergementDroite(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableDroite);

    Page<HebergementCarhos> getHebergementDashboard(Pageable page);

    void updateHebergementCarhos(Long id, @Valid HebergementCarhos hebergementCarhos);
}

