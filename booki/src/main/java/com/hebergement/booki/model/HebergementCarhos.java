package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
public class HebergementCarhos {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

       private Long id;

       @Enumerated(EnumType.STRING)
       @NotBlank(message="hebergement non figuré pour l'instant")
       private HebergementCarhosType hebergementCarhosType;

       @NotBlank(message="localisation non identifier")                  //ne peut pas être soumis vide
       private String localisation;

       @NotBlank(message="veillez donner un nom à votre hébergement")   //ne peut pas être soumis à zéro
       private String nom;

       @NotBlank(message="standing non renseigner")
       private String standing;

       @NotBlank(message="etat d'occupation doit être renseigné")
       private String etat;

       @NotNull(message="tarif est non null")
       @Positive
       private int tarif;

}