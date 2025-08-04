package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.HebergementCarhos;
import org.springframework.web.multipart.MultipartFile;

public interface HebergementService {
    HebergementCarhos getHebergementCarhosById(Long id);

    void supprimerHebergementCarhos(Long id);

    void enregistrerHebergement(HebergementCarhos hebergementCarhos, MultipartFile fichierImage);
}
