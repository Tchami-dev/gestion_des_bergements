package com.hebergement.booki.services.impl;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.services.inter.HebergementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void updateHebergementCarhos(Long id, HebergementCarhos hebergementCarhosModifie) {
        HebergementCarhos hebergementCarhos = getHebergementCarhosById(id);

        hebergementCarhos.setNom(hebergementCarhosModifie.getNom());
        hebergementCarhos.setDescription(hebergementCarhosModifie.getDescription());
        hebergementCarhos.setHebergementCarhosType(hebergementCarhosModifie.getHebergementCarhosType());
        hebergementCarhos.setHebergementCarhosSpecificite(hebergementCarhosModifie.getHebergementCarhosSpecificite());
        hebergementCarhos.setHebergementCarhosStatut(hebergementCarhosModifie.getHebergementCarhosStatut());
        hebergementCarhos.setImage(hebergementCarhosModifie.getImage());

        hebergementCarhosRepository.save(hebergementCarhos);
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

    @Override
    public Page<HebergementCarhos> getPageHebergementGauche(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableGauche) {
        Page<HebergementCarhos> pageGaucheResult;

        if ( filtre != null && keyword != null &&  !keyword.isEmpty()) {
            pageGaucheResult =  hebergementCarhosRepository.findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan( keyword, filtre, pageableGauche);

        }else if (filtre != null) {
            pageGaucheResult = hebergementCarhosRepository
                    .findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
                            filtre, pageableGauche);

            /*** cas du mot clé uniquement **/

        }else if (keyword != null && !keyword.isEmpty()) {
            pageGaucheResult = hebergementCarhosRepository
                    .findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
                            keyword, null, pageableGauche);

            /** en aucun cas **/
        } else {
            pageGaucheResult = hebergementCarhosRepository.findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(null, pageableGauche);
        }

        return pageGaucheResult;
    }

    @Override
    public Page<HebergementCarhos> getPageHebergementDroite(String keyword, HebergementCarhosSpecificite filtre, Pageable pageableDroite) {
        Page<HebergementCarhos> pageDroiteResult;
        /** cas du filtre et du motclé**/
        if ( filtre != null && keyword != null &&  !keyword.isEmpty()) {
            pageDroiteResult =   hebergementCarhosRepository.findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual( keyword, filtre, pageableDroite);
            /** cas du filtre uniquement **/

        }else if (filtre != null) {

            pageDroiteResult = hebergementCarhosRepository
                    .findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
                            filtre, pageableDroite);

            /*** cas du mot clé uniquement **/

        }else if (keyword != null && !keyword.isEmpty()) {

            pageDroiteResult = hebergementCarhosRepository
                    .findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
                            keyword, null, pageableDroite);
            /** en aucun cas **/
        } else {
            pageDroiteResult = hebergementCarhosRepository.findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(null, pageableDroite);
        }
        return pageDroiteResult;
    }

    @Override
    public Page<HebergementCarhos> getHebergementDashboard(Pageable page) {
        return hebergementCarhosRepository.findAll(page);
    }

}
