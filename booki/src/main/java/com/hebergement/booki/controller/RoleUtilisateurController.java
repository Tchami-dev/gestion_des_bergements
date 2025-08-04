package com.hebergement.booki.controller;


import com.hebergement.booki.repository.RoleUtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RoleUtilisateurController {

    private final RoleUtilisateurRepository roleUtilisateurRepository;

    @GetMapping("/daschboard_role_utilisateur")
    private String listingRole(Model model){
        model.addAttribute("utilisateurs", roleUtilisateurRepository.findAll());
        return "daschboard_role_utilisateur";
    }
}
