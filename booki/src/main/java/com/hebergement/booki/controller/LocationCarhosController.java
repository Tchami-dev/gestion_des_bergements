package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.LocationCarhos;
import com.hebergement.booki.model.RoleUtilisateurCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
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

    @GetMapping("/daschboard_location_carhos")
    public  String listingLocation(@RequestParam(defaultValue = "0") int page, Model model){
        int nbreElements = 4;
        Page<LocationCarhos> pageLocation = locationService.getPageLocation(PageRequest.of(page, nbreElements)) ;
        model.addAttribute("locations", pageLocation.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageLocation.getTotalPages()); // Pour les liens de pagination
        model.addAttribute("currentPage", page); // Page actuelle
        return  "daschboard_location_carhos";
    }

    @GetMapping("/location-carhos/nouveau")
    public  String creationLocation(Model model){
        model.addAttribute("location", new LocationCarhos());
        model.addAttribute("listeHebergement", locationService.getAllHebergement());
        return  "formulaire_enregistrement_location_carhos";
    }

    @PostMapping("/location-carhos")
    public String enregistrementCreation(@Valid @ModelAttribute("location") LocationCarhos locationCarhos,
                                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "formulaire_enregistrement_location_carhos";
        }
        locationService.enregistrerLocation(locationCarhos);
        return "redirect:/daschboard_location_carhos";
    }

    @PostMapping("/locations-carhos/delete/{id}")
    public String supprimerLocation(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        locationService.supprimerLocation(id);
        redirectAttributes.addFlashAttribute("errorMessage", "réservation introuvable.");
        return "redirect:/daschboard_location_carhos";
    }

    @GetMapping("/locations-carhos/edit/{id}")
    public String prechargementLocation(@PathVariable Long id, Model model) {
        LocationCarhos locationCarhos = locationService.getLocationCarhosById(id);
        model.addAttribute("location", locationCarhos);
        return "formulaire_enregistrement_location_carhos";
    }

    @PostMapping("/locations-carhos/{id}")
    public String modifierLocation(Model model, @PathVariable Long id,
           @Valid @ModelAttribute("location") LocationCarhos locationCarhos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formulaire_enregistrement_location_carhos";
        }
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
