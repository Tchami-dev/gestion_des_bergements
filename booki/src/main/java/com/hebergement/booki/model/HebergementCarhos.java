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

       @NotBlank(message="veillez donner un nom à votre hébergement s'il vous plaît")   //ne peut pas être soumis à zéro
       private String nom;


       @NotNull(message="le prix de votre hébergement n'est pas raisonnable")
       @Positive
       private double prix;



       private String image;

       @Min(1)
       @Max(5)
    private int nbreEtoile;

}