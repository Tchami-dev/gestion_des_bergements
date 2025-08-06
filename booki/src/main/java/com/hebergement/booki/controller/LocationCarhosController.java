package com.hebergement.booki.controller;

import com.hebergement.booki.model.LocationCarhos;
import com.hebergement.booki.services.inter.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LocationCarhosController {


    private  final LocationService locationService;

    @GetMapping("/daschboard_location_carhos")
    public  String listingLocation(@RequestParam(defaultValue = "0") int page, Model model){

        int nbreElements = 4;
        Page<LocationCarhos> pageUtilisateur = locationService.getPageLocation(PageRequest.of(page, nbreElements)) ;
        model.addAttribute("locations", pageUtilisateur.getContent()); // Liste actuelle (4 éléments) des hébergements de la page en cours
        model.addAttribute("totalPages", pageUtilisateur.getTotalPages()); // Pour les liens de pagination
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


}
