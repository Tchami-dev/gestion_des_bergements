package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "hebergement_carhos")
@Data
public class HebergementCarhos {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;

   @Enumerated(EnumType.STRING)
   @NotNull(message = "Merci de renseigner la classe de votre hébergement")
   private  HebergementCarhosType hebergementCarhosType;

       @Enumerated(EnumType.STRING)
       @NotNull(message = "veillez renseigner la catégorie de l'hébergement")
       private HebergementCarhosSpecificite hebergementCarhosSpecificite;

       @NotBlank(message="veillez donner un nom à votre hébergement s'il vous plaît")   //ne peut pas être soumis à zéro
       private String nom;

       @NotNull(message="le prix de votre hébergement n'est pas raisonnable")
       @Positive
       private double prix;

       @Column(name = "image_heberg")
       private String image;

       @Min(1)
       @Max(5)
       @Column(name = "nbre_etoile")
       private int nbreEtoile;

       @Lob   /** pour stocké des variables très longues de types string **/
       @Column(columnDefinition = "TEXT", name = "description_heberg")
       @NotBlank (message="Merci de donner des informations détail sur l'hébergement")
       private String description;

    @Lob
    @Column(columnDefinition = "TEXT", name = "capacite_heberg")
    @NotBlank (message="Merci de donner les atouts majeurs de l'hébergement")
    private String capacite;

    @Column(name = "satut_heberg")
    @Enumerated(EnumType.STRING)
    private HebergementCarhosStatut hebergementCarhosStatut;

     @Column(name = "localisation_heberg")
    private  String localisationHeberg;

    /********************* relation-de-multiplicité-entre-les-tables *************************/

    @OneToMany (mappedBy = "hebergementCarhos") /***  idication de la multiplicite **/
    private List<LocationCarhos> locationCarhos ;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private  TypesHebergementCarhos typesHebergementCarhos;

}