package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


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



       private String image;

       @Min(1)
       @Max(5)
    private int nbreEtoile;

       @Lob
       @Column(columnDefinition = "TEXT")
       @NotBlank (message="Merci de donner des informations détail sur l'hébergemnt")
       private String detail;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotBlank (message="Merci de donner les atouts majeurs de l'hébergemnt")
    private String atouts;

}