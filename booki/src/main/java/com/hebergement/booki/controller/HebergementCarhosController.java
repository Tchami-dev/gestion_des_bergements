package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
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


@Controller
@RequiredArgsConstructor
public class HebergementCarhosController {

    private final HebergementCarhosRepository hebergementCarhosRepository;
    private final HebergementService hebergementService;

    @Value("${upload.path}")
    private String uploadDir;

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



        Page<HebergementCarhos> pageGaucheResult;
        Page<HebergementCarhos> pageDroiteResult;

        /** cas du filtre et du motclé**/
        if ( filtre != null && keyword != null &&  !keyword.isEmpty()) {
            pageGaucheResult = hebergementCarhosRepository.findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan( keyword, filtre, pageableGauche);
            pageDroiteResult = hebergementCarhosRepository.findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual( keyword, filtre, pageableDroite);


            /** cas du filtre uniquement **/

        }else if (filtre != null) {
            pageGaucheResult = hebergementCarhosRepository
                    .findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
                            filtre, pageableGauche);

            pageDroiteResult = hebergementCarhosRepository
                    .findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
                            filtre, pageableDroite);

            /*** cas du mot clé uniquement **/

        }else if (keyword != null && !keyword.isEmpty()) {
            pageGaucheResult = hebergementCarhosRepository
                    .findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
                            keyword, null, pageableGauche);

            pageDroiteResult = hebergementCarhosRepository
                    .findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
                            keyword, null, pageableDroite);
            /** en aucun cas **/
        }else {
            pageGaucheResult = hebergementCarhosRepository.findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(null, pageableGauche);
            pageDroiteResult = hebergementCarhosRepository.findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(null, pageableDroite);

        }

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
    @GetMapping("/hebergement_carhos/nouveau")
    public String nouveauHebergementCarhos(Model model){
        model.addAttribute("hebergementCarhos", new HebergementCarhos());
        model.addAttribute("type", HebergementCarhosType.values());
        model.addAttribute("specificite", HebergementCarhosSpecificite.values());
        return "formulaire_enregistrement_hebergement_carhos";
    }

    /**** orientation du lien vers le dashbord paginé, et visualisation des enregistrements******/
    @GetMapping("/daschboard_carhos")
    public String afficherDashboard(@RequestParam(defaultValue = "0") int page, Model model) {
        int taillePage = 4; // Nombre d'hébergements par page

        Page<HebergementCarhos> pageHebergements = hebergementCarhosRepository.findAll(PageRequest.of(page, taillePage));   //indication du numéro de la page et du nombre d'élément par page

        model.addAttribute("hebergementsCarhos", pageHebergements.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageHebergements.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle

        return "daschboard_carhos"; // Nom du  fichier HTML de retour
    }


    /******** soumission d'enregistrement d'un hébergement***/

//    @PostMapping("/hebergement_carhos")
//    public String enregistrementHebergementCarhos(@Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
//                                                  BindingResult bindingResult, Model model, @RequestParam("fichier_image") MultipartFile fichier_image) {
//
//        // Gestion des erreurs de validation
//        if (bindingResult.hasErrors()) {
//
//            model.addAttribute("type", HebergementCarhosType.values());
//            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
//            return "formulaire_enregistrement_hebergement_carhos";
//        }
//
//
//        // Gestion du fichier image uploadé
//        if (!fichier_image.isEmpty()) {
//            try {
//                String fileName = fichier_image.getOriginalFilename();
//
//                // Créer le dossier s'il n'existe pas
//                // File directory = new File(uploadDir);
//                File directory = new File("T:/IUS/projet-de-soutenace-bts/images_upload");
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                // Créer le fichier dans le dossier
//                File saveFile = new File(directory, fileName);
//                fichier_image.transferTo(saveFile);
//
//                // Enregistre le nom du fichier dans l’objet
//                hebergementCarhos.setImage(fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//                model.addAttribute("uploadError", "Échec du téléchargement de l'image.");
//                return "formulaire_enregistrement_hebergement_carhos";
//            }
//        }
//
//        hebergementCarhosRepository.save(hebergementCarhos);
//        return "redirect:/daschboard_carhos";
//    }

    @PostMapping("/hebergement_carhos")
    public String enregistrementHebergementCarhos(
            @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
            BindingResult bindingResult,
            Model model,
            @RequestParam("fichier_image") MultipartFile fichier_image) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }

        try {
            hebergementService.enregistrerHebergement(hebergementCarhos, fichier_image);
        } catch (RuntimeException e) {
            model.addAttribute("uploadError", e.getMessage());
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }

        return "redirect:/daschboard_carhos";
    }


    /***** mise à jour des informations des hébergements ****/

    /*préchargement du formulaire */

    @GetMapping("/hebergement_carhos/edit/{id}")
    public String infoHebergementCarhos(@PathVariable long id, Model model){
        HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("l'hébergement repondant à l'id: "+ id+ "est introuvable"));
        model.addAttribute("hebergementCarhos", hebergementCarhos);
        model.addAttribute("type", HebergementCarhosType.values()) ;
        model.addAttribute("specificite", HebergementCarhosSpecificite.values());
        return "formulaire_enregistrement_hebergement_carhos";
    }

    /*mise à jour*/
    @PostMapping("/hebergement_carhos/{id}")
    public String actualiserHebergementCarhos(@PathVariable long id, @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos, BindingResult bindingResult, Model model){

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            model.addAttribute("specificite", HebergementCarhosSpecificite.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }

        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/";
    }


    /*********** supprimer un hébergement ******/
    @PostMapping("/hebergement_carhos/delete/{id}")
    public String supprimerHebergementCarhos(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hebergementService.supprimerHebergementCarhos(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hébergement supprimé avec succès.");
        return "redirect:/daschboard_carhos";
    }



    /********* informations détaillées  sur un hébergement ********/
    @GetMapping("/hebergement_carhos/détail/{id}")
    public String detailHebergementCarhos(@PathVariable("id") Long id, Model model){

        // Récupération de l'hébergement par son ID
        HebergementCarhos hebergement = hebergementService.getHebergementCarhosById(id);

        // Passage de l'objet à la vue
        model.addAttribute("hebergementCarhos", hebergement);
        return "detail_hebergement_carhos";

    }

}