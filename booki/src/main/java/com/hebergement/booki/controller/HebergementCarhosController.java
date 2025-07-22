package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
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
    public String enregistrementHebergementCarhos(
            @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
            BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile imageFile) {

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_hebergement_carhos";
        }

        // Gestion du fichier image uploadé
        if (!imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                String uploadDir = new File("src/main/resources/static/images").getAbsolutePath();

                File saveFile = new File(uploadDir, fileName);
                imageFile.transferTo(saveFile);

                // Enregistre le nom de fichier dans l'objet
                hebergementCarhos.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                // Tu peux ajouter une gestion des erreurs plus propre ici
            }
        }

        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/daschboard_carhos";
    }

    /***** mise à jour des informations des hébergements ****/

    @GetMapping("/hebergementCarhos/edit/{id}")
    public String supprimerHebergementCarhos(@PathVariable long id, Model model){
        HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("l'hébergement repondant à l'id: "+ id+ "est non identifier"));
        model.addAttribute("hebergementCarhos", hebergementCarhos);
        return "redirect:/daschboard_carhos";
    }

}