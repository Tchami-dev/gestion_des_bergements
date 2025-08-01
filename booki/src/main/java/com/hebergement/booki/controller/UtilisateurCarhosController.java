package com.hebergement.booki.controller;


import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.UtilisateurCarhosRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UtilisateurCarhosController {

    private final UtilisateurCarhosRepository utilisateurCarhosRepository;


    @GetMapping("/daschboard_utilisateur_carhos")
    public  String listingUtilisateur(Model model){
        model.addAttribute("utilisateurs", utilisateurCarhosRepository.findAll());
        return  "daschboard_utilisateur_carhos";
    }

    @GetMapping("/utilisateurs_carhos/nouveau")
    public  String creationUtilisateur(Model model){
        model.addAttribute("utilisateur", new UtilisateurCarhos());
        return  "formulaire_enregistrement_utilisateur_carhos";
    }

    @PostMapping("/utilisateurs_carhos")
    public  String sauvegardeUtilisateur(@Valid
             @ModelAttribute("utilisateur") UtilisateurCarhos utilisateurCarhos, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "formulaire_enregistrement_utilisateur_carhos";
        }
            utilisateurCarhosRepository.save(utilisateurCarhos);
            return "redirect:/daschboard_utilisateur_carhos";
    }

    @PostMapping("/utilisateurs_carhos/delete/{id}")
    public String SupprimerUtilisateur(@PathVariable Long id,
           Model model, RedirectAttributes redirectAttributes){
        utilisateurCarhosRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès.");
        return "redirect:/daschboard_utilisateur_carhos";
    }

}
