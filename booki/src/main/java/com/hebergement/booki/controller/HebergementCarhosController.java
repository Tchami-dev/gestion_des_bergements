package com.hebergement.booki.controller;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosType;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class HebergementCarhosController {

    private final HebergementCarhosRepository hebergementCarhosRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("hebergementsCarhos", hebergementCarhosRepository.findAll());
        return "index";
    }


    @GetMapping("/hebergementCarhos/nouveau")
    public String nouveauHebergementCarhos(Model model){
        model.addAttribute("hebergementCarhos", new HebergementCarhos());
        model.addAttribute("type", HebergementCarhosType.values());
        return "enregistrement_hebergement";
    }

    @PostMapping("/hebergementBooki")
    public String saveHebergementBooki(@Valid @ModelAttribute("hebergementBooki") HebergementCarhos hebergementBookiModel, BindingResult bindingResult){
        //bindingResult permet de gérer les erreurs
        if (bindingResult.hasErrors()){
            return "enregistrement_hebergement";
        }
        hebergementCarhosRepository.save(hebergementBookiModel);
        return "redirect:/";
    }



}