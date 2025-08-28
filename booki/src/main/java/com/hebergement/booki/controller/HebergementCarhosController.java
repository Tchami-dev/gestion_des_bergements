package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosType;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

import static com.hebergement.booki.utils.GeneralUtils.DOSSIER_DU_PROJET;


@Controller
@RequiredArgsConstructor

public class HebergementCarhosController {

    private final HebergementCarhosRepository hebergementCarhosRepository;

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
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

        //Déclaration de la liste des hébergements à afficher
        List<HebergementCarhos> hebergementCarhos;

        //Si un mot-clé est fourni et n'est pas vide, on filtre les hébergements dont le nom contient ce mot-clé
        if (keyword != null && !keyword.isEmpty()) {
            hebergementCarhos = hebergementCarhosRepository.findByNomContainingIgnoreCase(keyword);
        }
        //Sinon, on affiche tous les hébergements
        else {
            hebergementCarhos = hebergementCarhosRepository.findAll();
        }

        //On envoie la liste des hébergements filtrée (ou complète) à la vue (index.html)
        model.addAttribute("hebergementsCarhos", hebergementCarhos);

        //On renvoie aussi le mot-clé pour le réafficher dans le champ de recherche (utile pour l'expérience utilisateur)
        model.addAttribute("keyword", keyword);

        // Retour de la vue "index.html"
        return "index";
    }


    /************ création d'un hébergement ******/
    @GetMapping("/hebergement_carhos/nouveau")
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

    @PostMapping("/hebergement_carhos")
    public String enregistrementHebergementCarhos(@Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos,
                                                  BindingResult bindingResult, Model model, @RequestParam("fichier_image") MultipartFile fichier_image) {

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("type", HebergementCarhosType.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }


        // Gestion du fichier image uploadé
        if (!fichier_image.isEmpty()) {
            try {
                String fileName = fichier_image.getOriginalFilename();

                // Créer le dossier s'il n'existe pas
               // File directory = new File(uploadDir);
                File directory = new File(DOSSIER_DU_PROJET);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Créer le fichier dans le dossier
                File saveFile = new File(directory, fileName);
                fichier_image.transferTo(saveFile);

                // Enregistre le nom du fichier dans l’objet
                hebergementCarhos.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("uploadError", "Échec du téléchargement de l'image.");
                return "formulaire_enregistrement_hebergement_carhos";
            }
        }

        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/daschboard_carhos";
    }

    /***** mise à jour des informations des hébergements ****/

    /*préchargement du formulaire */

    @GetMapping("/hebergement_carhos/edit/{id}")
    public String infoHebergementCarhos(@PathVariable long id, Model model){
        HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("l'hébergement repondant à l'id: "+ id+ "est introuvable"));
        model.addAttribute("hebergementCarhos", hebergementCarhos);
        model.addAttribute("type", HebergementCarhosType.values()) ;
        return "formulaire_enregistrement_hebergement_carhos";
    }

    /*mise à jour*/
    @PostMapping("/hebergement_carhos/{id}")
    public String actualiserHebergementCarhos(@PathVariable long id, @Valid @ModelAttribute("hebergementCarhos") HebergementCarhos hebergementCarhos, BindingResult bindingResult, Model model){

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", HebergementCarhosType.values());
            return "formulaire_enregistrement_hebergement_carhos";
        }
        hebergementCarhosRepository.save(hebergementCarhos);
        return "redirect:/";
    }


    /*********** supprimer un hébergement ******/
    @PostMapping("/hebergement_carhos/delete/{id}")
    public String supprimerHebergementCarhos(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        hebergementCarhosRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hébergement supprimé avec succès.");
        return "redirect:/daschboard_carhos";
    }


    /********* informations détaillées  sur un hébergement ********/
@GetMapping("/hebergement_carhos/détail/{id}")
    public String detailHebergementCarhos(@PathVariable("id") Long id, Model model){
    // Récupération de l'hébergement par son ID
    HebergementCarhos hebergementCarhos = hebergementCarhosRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("l' hébergement introuvable avec l'ID : " + id));

    // Passage de l'objet à la vue
    model.addAttribute("hebergementCarhos", hebergementCarhos);
    return "detail_hebergement_carhos";

}

//Model model permet de transporter un object, du controller vers la vue


}