package com.hebergement.booki.controller;


import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.services.inter.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UtilisateurCarhosController {

   private final UtilisateurService utilisateurService;


    @GetMapping("/daschboard_utilisateur_carhos")
    public  String listingUtilisateur( @RequestParam(defaultValue = "0") int page, Model model){

        int nbreElements = 4;
        Page<UtilisateurCarhos> pageUtilisateur = utilisateurService.getPageUtilisateur(PageRequest.of(page, nbreElements)) ;

        model.addAttribute("utilisateurs", pageUtilisateur.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageUtilisateur.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle

        return  "daschboard_utilisateur_carhos";
    }

    @GetMapping("/utilisateurs-carhos/nouveau")
    public  String creationUtilisateur(Model model){
        model.addAttribute("utilisateur", new UtilisateurCarhos());
        return  "formulaire_enregistrement_utilisateur_carhos";
    }

    @PostMapping("/utilisateurs-carhos")
    public  String sauvegardeUtilisateur(@Valid
             @ModelAttribute("utilisateur") UtilisateurCarhos utilisateurCarhos, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "formulaire_enregistrement_utilisateur_carhos";
        }
        utilisateurService.enregistrerUtilisateur(utilisateurCarhos);
            return "redirect:/daschboard_utilisateur_carhos";
    }

    @PostMapping("/utilisateurs-carhos/delete/{id}")
    public String SupprimerUtilisateur(@PathVariable Long id,
           Model model, RedirectAttributes redirectAttributes){
       utilisateurService.supprimerUtilisateur(id);
            redirectAttributes.addFlashAttribute("errorMessage", "Utilisateur introuvable.");
        return "redirect:/daschboard_utilisateur_carhos";
    }

    @GetMapping("/utilisateurs-carhos/edit/{id}")
     public String prechargementUtilisateur(Model model, @PathVariable Long id){
        UtilisateurCarhos utilisateurCarhos= utilisateurService.getUtilisateurCarhosById(id);
        model.addAttribute("utilisateur", utilisateurCarhos);
        return  "formulaire_enregistrement_utilisateur_carhos";
    }

    @PostMapping("/utilisateurs-carhos/{id}")
    public  String modificationUtilisateur(Model model, @PathVariable Long id,
        @Valid @ModelAttribute("utilisateur") UtilisateurCarhos utilisateurCarhos, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_utilisateur_carhos";
        }
        utilisateurService.enregistrerUtilisateur(utilisateurCarhos);
        return "daschboard_utilisateur_carhos";
    }
}
