package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.UtilisateurCarhosRepository;
import com.hebergement.booki.services.inter.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class UtilisateurServiceImp implements UtilisateurService {


    private final UtilisateurCarhosRepository utilisateurCarhosRepository;

    /** le constructeur de ton service d’implémentation qui permet de faire l’injection de dépendances par constructeur**/
    public UtilisateurServiceImp(UtilisateurCarhosRepository utilisateurCarhosRepository) {
        this.utilisateurCarhosRepository = utilisateurCarhosRepository;
    }

    @Override
    public UtilisateurCarhos getUtilisateurCarhosById(Long id) {
        return utilisateurCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));
    }

    @Override
    public void enregistrerUtilisateur(UtilisateurCarhos utilisateurCarhos) {
//        String password = utilisateurCarhos.getMotDePasseUtilisateur();
//        utilisateurCarhos.setMotDePasseUtilisateur(passwordEncoder.encode(password));
          utilisateurCarhosRepository.save(utilisateurCarhos);
    }

    @Override
    public void supprimerUtilisateur(Long id) {
        UtilisateurCarhos utilisateurCarhos = utilisateurCarhosRepository.findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));

         utilisateurCarhosRepository.delete(utilisateurCarhos);
    }

    @Override
    public Page<UtilisateurCarhos> getPageUtilisateur(Pageable page) {
        return utilisateurCarhosRepository.findAll(page);
    }
}