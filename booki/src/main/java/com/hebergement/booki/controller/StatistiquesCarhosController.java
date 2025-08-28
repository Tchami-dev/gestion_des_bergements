package com.hebergement.booki.controller;


import com.hebergement.booki.services.inter.HebergementService;
import com.hebergement.booki.services.inter.StatisquesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatistiquesCarhosController {

    @Controller
    public class StatistiquesCarhos {

        @Autowired
        private StatisquesService statistiquesService;

        @Autowired
        private HebergementService hebergementService;

        @GetMapping("/dashboard-universel-carhos")
        public String dashboardUniversel(Model model) {
            model.addAttribute("stats", statistiquesService.getStatistiques());
            return "dashboard_universel";
        }
    }


}
