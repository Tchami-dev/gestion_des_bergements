package com.hebergement.booki.controller;


import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.RoleUtilisateurRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RoleUtilisateurController {

    private final RoleUtilisateurRepository roleUtilisateurRepository;

    @GetMapping("/daschboard_role_utilisateur")
    private String listingRole(@RequestParam(defaultValue ="0") int page,Model model ){

        int nbreElements = 4;
        Page<RoleUtilisateurCarhos> pageRoleUtiisateur = roleUtilisateurRepository.findAll(PageRequest.of(page, nbreElements)) ;

        model.addAttribute("roles", pageRoleUtiisateur.getContent());
        model.addAttribute("totalPages", pageRoleUtiisateur.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle
        return "daschboard_role_utilisateur";
    }

    @GetMapping("/role-utilisateurs-carhos/nouveau")
    public String creationRole(Model model){
        model.addAttribute("role", new RoleUtilisateurCarhos());
        return "formulaire_enregistrement_role";
    }

    @PostMapping("/role-carhos")
    public  String sauvegardeRole(@Valid
                                         @ModelAttribute("role") RoleUtilisateurCarhos roleUtilisateurCarhos,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "formulaire_enregistrement_role";
        }
        roleUtilisateurRepository.save(roleUtilisateurCarhos);
        return "redirect:/daschboard_role_utilisateur";
    }

    @PostMapping("/role-utilisateurs-carhos/delete/{id}")
    public String supprimerRole(@PathVariable Long id,
       Model model, RedirectAttributes redirectAttributes){
        if (roleUtilisateurRepository.existsById(id)) {
           roleUtilisateurRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Rôle supprimé avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", " introuvable.");
        }
        return "redirect:/daschboard_role_utilisateur";
    }

    @GetMapping("/role-utilisateurs-carhos/edit/{id}")
    public String prechargementRole(@PathVariable Long id, Model model){
        RoleUtilisateurCarhos roleUtilisateurCarhos= roleUtilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("le rôle repondant à l'id: "+ id+ "est introuvable"));
        model.addAttribute("role", roleUtilisateurCarhos);
        return  "formulaire_enregistrement_role";
    }

    @PostMapping("/role-utilisateurs-carhos/{id}")
    public String modifierRole(Model model, @PathVariable Long id,
         @Valid @ModelAttribute("role") RoleUtilisateurCarhos roleUtilisateurCarhos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_role";
        }
        roleUtilisateurRepository.save(roleUtilisateurCarhos);
        return "daschboard_utilisateur_carhos";
    }
}





