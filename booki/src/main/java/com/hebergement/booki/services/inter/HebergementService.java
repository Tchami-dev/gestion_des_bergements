package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface HebergementService {


    HebergementCarhos getHebergementCarhosById(Long id);

     void supprimerHebergementCarhos(Long id);

    void enregistrerHebergement(HebergementCarhos hebergementCarhos, MultipartFile fichierImage);

    Page<HebergementCarhos> getPageHebergementGauche(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableGauche);

    Page<HebergementCarhos> getPageHebergementDroite(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableDroite);
}
