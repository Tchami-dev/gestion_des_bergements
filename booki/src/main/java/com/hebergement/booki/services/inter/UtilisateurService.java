package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.UtilisateurCarhos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UtilisateurService {

    UtilisateurCarhos getUtilisateurCarhosById(Long id);

    void enregistrerUtilisateur(UtilisateurCarhos utilisateurCarhos);

    void supprimerUtilisateur (Long id);

    Page<UtilisateurCarhos> getPageUtilisateur(Pageable page);
}
