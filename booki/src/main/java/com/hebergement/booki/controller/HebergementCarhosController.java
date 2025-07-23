package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosType;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Controller
@RequiredArgsConstructor
public class HebergementCarhosController {

    private final HebergementCarhosRepository hebergementCarhosRepository;

    /**** lien vers la page d'accueil****/
    @GetMapping("/index")
public String accueil(){
        return "redirect:/";
}
    /******* listing des hébergements **********/
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("hebergementsCarhos", hebergementCarhosRepository.findAll());
        return "index";
    }

/************ création d'un hébergement ******/
    @GetMapping("/hebergementCarhos/nouveau")
       public String nouveauHebergementCarhos(Model model){
            model.addAttribute("hebergementCarhos", new HebergementCarhos());
            model.addAttribute("type", HebergementCarhosType.values());
            return "formulaire_enregistrement_hebergement_carhos";
       }

       /**** orientation du lien vers le dashbord, et visualisation des enregistrements******/
       @GetMapping("/daschboard_carhos")
       public String afficherDashboard(Model model) {
           model.addAttribute("hebergementsCarhos", hebergementCarhosRepository.findAll());
           return "daschboard_carhos";
       }



    /******** soumission d'enregistrement d'un hébergement***/
    @PostMapping("/hebergementCarhos")
    public String enregistrementHebergementCarhos(@Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
                                                  BindingResult bindingResult, Model model, @RequestParam("fichier_image") MultipartFile fichier_image) {

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("type", HebergementCarhosType.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }


        // Gestion du fichier image uploadé
        if (!fichier_image.isEmpty()) {
            /*vérification et enregistrement de l'image*/
            try {
                String fileName = fichier_image.getOriginalFilename();    /*récupération du nom originel du fichier*/
                String uploadDir = new File("src/main/resources/static/images_upload").getAbsolutePath(); /* création d'un chemin vers le dossier images_upload du dd*/

                File saveFile = new File(uploadDir, fileName);  /*création d'une reférence à l'endroit du fichier stocké*/
                fichier_image.transferTo(saveFile); /*enregistrement définitive de l'image à l'endroit définit */

                // Enregistre le nom de fichier dans l'objet
                hebergementCarhos.setImage(fileName); /*stockage du nom du fichier*/
            } catch (Exception e) /*gestion des erreures*/{
                e.printStackTrace();
                // ajout d'une gestion des erreurs plus propre ici
            }
        }

        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/daschboard_carhos";
    }

    /***** mise à jour des informations des hébergements ****/

    @GetMapping("/hebergementCarhos/edit/{id}")
    public String infoHebergementCarhos(@PathVariable long id, Model model){
        HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("l'hébergement repondant à l'id: "+ id+ "est introuvable"));
        model.addAttribute("hebergementCarhos", hebergementCarhos);
        model.addAttribute("type", HebergementCarhosType.values()) ;
        return "formulaire_enregistrement_hebergement_carhos";
    }

    @PostMapping("/hebergementCarhos/{id}")
    public String actualiserHebergementCarhos(@PathVariable long id, @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos, BindingResult bindingResult, Model model){

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }
        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/";
    }

    @GetMapping("/hebergementCarhos/delete/{id}")
    public String supprimerHebergementCarhos(@PathVariable Long id, Model model){
        hebergementCarhosRepository.deleteById(id);
        return "redirect:/daschboard_carhos";
    }


}