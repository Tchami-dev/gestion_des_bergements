package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
import com.hebergement.booki.model.HebergementCarhosStatut;
import com.hebergement.booki.model.HebergementCarhosType;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.services.inter.HebergementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.hebergement.booki.utils.GeneralUtils.DOSSIER_DU_PROJET;


@Controller
@RequiredArgsConstructor
public class HebergementCarhosController {

//    private final HebergementCarhosRepository hebergementCarhosRepository;
    private final HebergementService hebergementService;

    /**** lien vers la page d'accueil****/
    @GetMapping("/index")
    public String accueil(){
        return "redirect:/";
    }

    /******* listing des hébergements **********/
    //Méthode qui gère les requêtes GET vers la page d'accueil "/"
    @GetMapping("/")
    public String index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageGauche", defaultValue = "0") int pageGauche,
            @RequestParam(value = "pageDroite", defaultValue = "0") int pageDroite,
            @RequestParam(value = "filtre", required = false) HebergementCarhosSpecificite filtre,
            Model model) {



        int taillePageGauche = 6;
        int taillePageDroite = 3;

        Pageable pageableGauche = PageRequest.of(pageGauche, taillePageGauche);
        Pageable pageableDroite = PageRequest.of(pageDroite, taillePageDroite);

        Page<HebergementCarhos> pageGaucheResult =  hebergementService.getPageHebergementGauche(keyword, filtre, pageableGauche);
        Page<HebergementCarhos> pageDroiteResult =  hebergementService.getPageHebergementDroite(keyword, filtre, pageableDroite);

        model.addAttribute("gauche", pageGaucheResult.getContent());
        model.addAttribute("totalPagesGauche", pageGaucheResult.getTotalPages());
        model.addAttribute("currentPageGauche", pageGauche);

        model.addAttribute("droite", pageDroiteResult.getContent());
        model.addAttribute("totalPagesDroite", pageDroiteResult.getTotalPages());
        model.addAttribute("currentPageDroite", pageDroite);

        model.addAttribute("keyword", keyword);
        model.addAttribute("filtreActif", filtre);
        model.addAttribute("hebergementCarhosSpecificite");

        return "index";
    }

    /************ création d'un hébergement ******/
    @GetMapping("/hebergement-carhos/nouveau")
    public String nouveauHebergementCarhos(Model model){
        model.addAttribute("hebergementCarhos", new HebergementCarhos());
        model.addAttribute("type", HebergementCarhosType.values());
        model.addAttribute("specificite", HebergementCarhosSpecificite.values());
        model.addAttribute("etat", HebergementCarhosStatut.values());
        return "formulaire_enregistrement_hebergement_carhos";
    }

    /**** orientation du lien vers le dashbord paginé, et visualisation des enregistrements******/
    @GetMapping("/daschboard_hebergement_carhos")
    public String afficherDashboard(@RequestParam(defaultValue = "0") int page, Model model) {
        int taillePage = 4; // Nombre d'hébergements par page

        Page<HebergementCarhos> pageHebergements = hebergementService.getHebergementDashboard(PageRequest.of(page, taillePage));   //indication du numéro de la page et du nombre d'élément par page

        model.addAttribute("hebergementsCarhos", pageHebergements.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageHebergements.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle

           return "daschboard_hebergement_carhos"; // Nom du  fichier HTML de retour
       }

    /******** soumission d'enregistrement d'un hébergement***/

    @PostMapping("/hebergement-carhos")
    public String enregistrementHebergementCarhos(
            @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
            BindingResult bindingResult,
            Model model,
            @RequestParam("fichier_image") MultipartFile fichierImage
            ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            model.addAttribute("etat", HebergementCarhosStatut.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }

        try {
            hebergementService.enregistrerHebergement(hebergementCarhos, fichierImage);
        } catch (RuntimeException e) {
            model.addAttribute("uploadError", e.getMessage());
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            model.addAttribute("etat", HebergementCarhosStatut.values());
            e.printStackTrace();
            return "formulaire_enregistrement_hebergement_carhos";
        }
        return "redirect:/daschboard_hebergement_carhos";
    }


    /***** mise à jour des informations des hébergements ****/

    /*préchargement du formulaire */

    @GetMapping("/hebergement-carhos/edit/{id}")
    public String infoHebergementCarhos(@PathVariable Long id, Model model){
        HebergementCarhos hebergementCarhos =hebergementService.getHebergementCarhosById(id);
        model.addAttribute("hebergementCarhos", hebergementCarhos);
        model.addAttribute("type", HebergementCarhosType.values()) ;
        model.addAttribute("specificite", HebergementCarhosSpecificite.values());
        model.addAttribute("etat", HebergementCarhosStatut.values());
        return "formulaire_enregistrement_hebergement_carhos";
    }


    /*mise à jour*/
    @PostMapping("/hebergement-carhos/{id}")
    public String actualiserHebergementCarhos(@PathVariable Long id, @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
                                              BindingResult bindingResult, Model model,
                                              @RequestParam("fichier_image") MultipartFile fichierImage,
                                              @RequestParam("ancienne_image") String ancienneImage
                                              ) throws IOException{

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            model.addAttribute("etat", HebergementCarhosStatut.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }
        // Vérifie s'il y a une nouvelle image
        if (fichierImage != null && !fichierImage.isEmpty()) {
            // ⚡ Nouvelle image → upload et remplace
            String nomImage = fichierImage.getOriginalFilename();
            Path chemin = Paths.get(DOSSIER_DU_PROJET, nomImage);
            Files.copy(fichierImage.getInputStream(), chemin, StandardCopyOption.REPLACE_EXISTING);

            hebergementCarhos.setImage(nomImage);
        } else {
            // Pas de nouvelle → garde l’ancienne
            hebergementCarhos.setImage(ancienneImage);
        }

        //Sauvegarde l’hébergement mis à jour
        hebergementService.updateHebergementCarhos(id, hebergementCarhos);

        return "redirect:/";
    }


    /*********** supprimer un hébergement ******/
    @PostMapping("/hebergement-carhos/delete/{id}")
    public String supprimerHebergementCarhos(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hebergementService.supprimerHebergementCarhos(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hébergement supprimé avec succès.");
        return "redirect:/daschboard_hebergement_carhos";
    }



    /********* informations détaillées  sur un hébergement ********/
    @GetMapping("/hebergement-carhos/détail/{id}")
    public String detailHebergementCarhos(@PathVariable("id") Long id, Model model){

        // Récupération de l'hébergement par son ID
        HebergementCarhos hebergement = hebergementService.getHebergementCarhosById(id);

        // Passage de l'objet à la vue
        model.addAttribute("hebergementCarhos", hebergement);
        return "detail_hebergement_carhos";

    }

}