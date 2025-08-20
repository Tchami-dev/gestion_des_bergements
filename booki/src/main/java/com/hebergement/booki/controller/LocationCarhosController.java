package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.LocationCarhos;
import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.model.UtilisateurCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.services.inter.HebergementService;
import com.hebergement.booki.services.inter.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LocationCarhosController {

    @Autowired
    private  final LocationService locationService;

    @Autowired
    private HebergementService hebergementService;

    @GetMapping("/daschboard_location_carhos")
    public  String listingLocation(@RequestParam(defaultValue = "0") int page, Model model){
        int nbreElements = 4;
        Page<LocationCarhos> pageLocation = locationService.getPageLocation(PageRequest.of(page, nbreElements)) ;
        model.addAttribute("locations", pageLocation.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageLocation.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle
        return  "daschboard_location_carhos";
    }

    // 👉 Affichage du formulaire réservation avec préchargement d’un hébergement
    @GetMapping("/location-carhos/nouveau")
    public String creationLocation(@RequestParam(name = "hebergementId", required = false) Long hebergementId,
                                   Model model) {

        HebergementCarhos hebergementCarhos = hebergementService.getHebergementCarhosById(hebergementId);
        LocationCarhos locationCarhos = new LocationCarhos();
            // Préremplit l’hébergement dans la réservation
            locationCarhos.setHebergementCarhos(hebergementCarhos);
        model.addAttribute("location", locationCarhos);
        return "formulaire_enregistrement_location_carhos";
    }

    // 👉 Enregistrement de la réservation
    @PostMapping("/location-carhos")
    public String enregistrementCreation(@Valid @ModelAttribute("location") LocationCarhos locationCarhos,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_location_carhos";
        }

        // Recharge l’hébergement complet via l’ID envoyé en hidden
        if (locationCarhos.getHebergementCarhos() != null &&
                locationCarhos.getHebergementCarhos().getId() != null) {

            HebergementCarhos hebergement = hebergementService
                    .getHebergementCarhosById(locationCarhos.getHebergementCarhos().getId());

            locationCarhos.setHebergementCarhos(hebergement);
        }

        // Sauvegarde la réservation
        locationService.enregistrerLocation(locationCarhos);

        return "redirect:/index";
    }

    @PostMapping("/locations-carhos/delete/{id}")
    public String supprimerLocation(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        try {
            locationService.supprimerLocation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation supprimée et hébergement libéré.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Réservation introuvable.");
        }

        return "redirect:/daschboard_location_carhos";
    }

    @GetMapping("/locations-carhos/edit/{id}")
    public String prechargementLocation(@PathVariable Long id, Model model) {
        // Récupère la réservation existante
        LocationCarhos locationCarhos = locationService.getLocationCarhosById(id);

        // Assure que l'hébergement lié est chargé (pas null)
        if (locationCarhos.getHebergementCarhos() == null) {
            // Optionnel : lancer une exception ou rediriger
            throw new RuntimeException("Aucun hébergement associé à cette réservation !");
        }

        model.addAttribute("location", locationCarhos);
        return "formulaire_enregistrement_location_carhos";
    }

    @PostMapping("/locations-carhos/{id}")
    public String modifierLocation(Model model,
                                   @PathVariable Long id,
                                   @Valid @ModelAttribute("location") LocationCarhos locationCarhos,
                                   BindingResult bindingResult,
                                   @RequestParam Long hebergementId) {
        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_location_carhos";
        }

        // Lier à l'hébergement existant
        HebergementCarhos hebergement = hebergementService.getHebergementCarhosById(hebergementId);
        locationCarhos.setHebergementCarhos(hebergement);

        locationService.enregistrerLocation(locationCarhos);
        return "daschboard_location_carhos";
    }


    @GetMapping("/locations-carhos/détail/{id}")
    public String detailLocationCarhos(@PathVariable("id") Long id, Model model) {
        // Récupération de l'utilisateur par son ID
        LocationCarhos locationCarhos = locationService.getLocationCarhosById(id);
        // Passage de l'objet à la vue

        model.addAttribute("location", locationCarhos);
        return "detail_reservation_carhos";
    }

}
