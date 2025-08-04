package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.services.inter.HebergementService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.hebergement.booki.utils.GeneralUtils.DOSSIER_DU_PROJET;

@Service
public class HebergementServiceImp implements HebergementService {

    private final HebergementCarhosRepository hebergementCarhosRepository;

    public HebergementServiceImp(HebergementCarhosRepository hebergementCarhosRepository) {
        this.hebergementCarhosRepository = hebergementCarhosRepository;
    }


    @Override
    public HebergementCarhos getHebergementCarhosById(Long id) {
        return hebergementCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));
    }

    @Override
    public void supprimerHebergementCarhos(Long id) {
        HebergementCarhos hebergement = hebergementCarhosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hébergement introuvable avec l'ID : " + id));

        hebergementCarhosRepository.delete(hebergement);
    }

    @Override
    public void enregistrerHebergement(HebergementCarhos hebergementCarhos, MultipartFile fichierImage) {
        if (fichierImage != null && !fichierImage.isEmpty()) {
            String fileName = fichierImage.getOriginalFilename();
            File directory = new File(DOSSIER_DU_PROJET);
            if (!directory.exists())
                directory.mkdirs();

            File saveFile = new File(directory, fileName);
            try {
                fichierImage.transferTo(saveFile);
                hebergementCarhos.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Échec du téléchargement de l'image.", e);
            }
        }

        hebergementCarhosRepository.save(hebergementCarhos);
    }
}
