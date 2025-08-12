package com.hebergement.booki.services.impl;


import com.hebergement.booki.model.HebergementCarhosStatut;
import com.hebergement.booki.model.StatistiquesCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import com.hebergement.booki.services.inter.StatisquesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisquesServiceImpl implements StatisquesService {

    @Autowired
    private HebergementCarhosRepository hebergementCarhosRepository;

    @Override
    public StatistiquesCarhos getStatistiques() {
        StatistiquesCarhos statistiquesCarhos = new StatistiquesCarhos();

        // Hébergements
        statistiquesCarhos.setHebergementTotal(hebergementCarhosRepository.count());
        statistiquesCarhos.setHebergementLibre(hebergementCarhosRepository.countByHebergementCarhosStatut(HebergementCarhosStatut.LIBRE));
        statistiquesCarhos.setHebergementOccupe(hebergementCarhosRepository.countByHebergementCarhosStatut(HebergementCarhosStatut.OCCUPE));

        return statistiquesCarhos;
    }
}
