package com.hebergement.booki.controller;


import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.RoleUtilisateurRepository;
import com.hebergement.booki.services.inter.RoleUtilisateurService;
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


    private final RoleUtilisateurService roleUtilisateurService;

    @GetMapping("/daschboard_role_utilisateur")
    private String listingRole(@RequestParam(defaultValue = "0") int page, Model model) {

        int nbreElements = 4;
        Page<RoleUtilisateurCarhos> pageRoleUtiisateur = roleUtilisateurService.getPageRoleUtilisateur(PageRequest.of(page, nbreElements));

        model.addAttribute("roles", pageRoleUtiisateur.getContent());
        model.addAttribute("totalPages", pageRoleUtiisateur.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle
        return "daschboard_role_utilisateur";
    }

    @GetMapping("/role-utilisateurs-carhos/nouveau")
    public String creationRole(Model model) {
        model.addAttribute("role", new RoleUtilisateurCarhos());
        return "formulaire_enregistrement_role";
    }

    @PostMapping("/role-carhos")
    public String sauvegardeRole(@Valid
                                 @ModelAttribute("role") RoleUtilisateurCarhos roleUtilisateurCarhos,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_role";
        }
        roleUtilisateurService.enregistrerRoleUtilisateur(roleUtilisateurCarhos);
        return "redirect:/daschboard_role_utilisateur";
    }

    @PostMapping("/role-utilisateurs-carhos/delete/{id}")
    public String supprimerRole(@PathVariable Long id,
                                Model model, RedirectAttributes redirectAttributes) {
        roleUtilisateurService.supprimerRoleUtilisateur(id);
        redirectAttributes.addFlashAttribute("errorMessage", "Utilisateur introuvable.");
        return "redirect:/daschboard_role_utilisateur";
    }

    @GetMapping("/role-utilisateurs-carhos/edit/{id}")
    public String prechargementRole(@PathVariable Long id, Model model) {
        RoleUtilisateurCarhos roleUtilisateurCarhos = roleUtilisateurService.getRoleUtilisateur(id);
        model.addAttribute("role", roleUtilisateurCarhos);
        return "formulaire_enregistrement_role";
    }

    @PostMapping("/role-utilisateurs-carhos/{id}")
    public String modifierRole(Model model, @PathVariable Long id,
                               @Valid @ModelAttribute("role") RoleUtilisateurCarhos roleUtilisateurCarhos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_role";
        }
        roleUtilisateurService.enregistrerRoleUtilisateur(roleUtilisateurCarhos);
        return "daschboard_utilisateur_carhos";
    }

    /********* informations détaillées  sur un hébergement ********/
    @GetMapping("/role-utilisateurs-carhos/détail/{id}")
    public String detailHebergementCarhos(@PathVariable("id") Long id, Model model) {

        // Récupération de l'utilisateur par son ID
        RoleUtilisateurCarhos roleUtilisateurCarhos = roleUtilisateurService.getRoleUtilisateur(id);

        // Passage de l'objet à la vue
        model.addAttribute("role", roleUtilisateurCarhos);
        return "detail_role_carhos";


    }
}





