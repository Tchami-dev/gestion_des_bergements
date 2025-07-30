package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "location_carhos")
@Data
public class LocationCarhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_location")
    private Long idLocation;


    @NotBlank(message = "Merci de renseigner la date de début de votre séjour")
    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @NotBlank(message = "Merci de reseigner la date de fin de votre séjour")
    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "statut_hebergement")
    private String statutHebergement;

    /******* relation-de-multiplicité-entre-les-tables  *******/

    /**plusieurs locations peuvent être effectuées par le même utilisateur**/
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private  UtilisateurCarhos utilisateurCarhos;

    /**plusieurs locations peuvent être effectuées pour le même hébergement **/
    @ManyToOne
    @JoinColumn(name = "id_heberg")
    private HebergementCarhos hebergementCarhos;

    @OneToMany(mappedBy = "locationCarhos")
    private  List<PayementCarhos> payementCarhos;





}
