package com.hebergement.booki.controller;

import com.hebergement.booki.repository.LocationCarhosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LocationCarhosController {

    private  final LocationCarhosRepository locationCarhosRepository;

    @GetMapping("/dashboard_resvation")
    public String listingReserbation(Model model){
        model.addAttribute("reservations", locationCarhosRepository.findAll());
        return "dashboard_resvation";
    }
}
