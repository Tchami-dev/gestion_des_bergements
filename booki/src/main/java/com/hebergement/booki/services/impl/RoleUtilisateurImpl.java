package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.RoleUtilisateurRepository;
import com.hebergement.booki.services.inter.RoleUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleUtilisateurImpl implements RoleUtilisateurService {

    @Autowired
    private final RoleUtilisateurRepository roleUtilisateurRepository;

    public RoleUtilisateurImpl (RoleUtilisateurRepository roleUtilisateurRepository){
        this.roleUtilisateurRepository = roleUtilisateurRepository;
    }


    @Override
    public RoleUtilisateurCarhos getRoleUtilisateur(Long id) {
        return roleUtilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));
    }

    @Override
    public void enregistrerRoleUtilisateur(RoleUtilisateurCarhos roleUtilisateurCarhos) {
        roleUtilisateurRepository.save(roleUtilisateurCarhos);
    }

    @Override
    public void supprimerRoleUtilisateur(Long id) {
        RoleUtilisateurCarhos roleUtilisateurCarhos = roleUtilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("rôle introuvable avec l'ID : " + id));
        roleUtilisateurRepository.delete(roleUtilisateurCarhos);

    }

    @Override
    public Page<RoleUtilisateurCarhos> getPageRoleUtilisateur(Pageable page) {
        return roleUtilisateurRepository.findAll(page);
    }
}
