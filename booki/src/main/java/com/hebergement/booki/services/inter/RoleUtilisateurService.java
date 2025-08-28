package com.hebergement.booki.services.inter;

import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleUtilisateurService {

    RoleUtilisateurCarhos getRoleUtilisateur(Long id);

    void enregistrerRoleUtilisateur(RoleUtilisateurCarhos roleUtilisateurCarhos);

    void supprimerRoleUtilisateur (Long id);

    Page<RoleUtilisateurCarhos> getPageRoleUtilisateur(Pageable page);


}
