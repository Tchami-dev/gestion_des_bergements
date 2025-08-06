package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "location_carhos")
@Data
@ToString(exclude = {"utilisateurCarhos", "hebergementCarhos", "payementCarhos"})
public class LocationCarhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_location")
    private Long idLocation;

    @NotNull(message = "Merci de renseigner la date de début de votre séjour")
    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Future //la date doit être dans le future
    @NotNull(message = "Merci de reseigner la date de fin de votre séjour")
    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "nom_client")
    @NotBlank(message = "Merci de renseigner votre: Nom")
    private String nomClient;

    @Column(name = "prenom_client")
    @NotBlank(message = "Merci de renseigner votre: Prénom")
    private  String prenomClient;

    @Column(name = "num_tel_client")
    @NotBlank(message = "Merci de renseigner votre: Numéro de Téléphone")
    @Pattern(regexp = "^\\+237[\\s\\-]?[0-9]{9}$",
            message = "Numéro de téléphone invalide. Il doit commencer par +237 suivi de 9 chiffres.")
    private String numTelClient;

    @Column(name = "email_client")
    @NotBlank(message = "Merci de renseigner votre: boîte mail ")
    private  String emailClient;

    @Column(name = "mot_de_passe_client")
    @NotBlank(message = "Merci de renseigner: votre mot de passe")
    private  String motDePasseUtilisateur;


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

//@NotBlanck est utilisé uniquement sur les types String



}
