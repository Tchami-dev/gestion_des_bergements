package com.hebergement.booki.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "types_hebergement_carhos")
@Data
public class TypesHebergementCarhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private  Long idType;

    private  String classes;

    private Long prix;

    private  String specificites;

    private String equipements;

    @Column(name = "libelle_type")   /* definit la manière dont l'attribut sera écrit dans la BD */
    private  String libelleType;


    @OneToMany(mappedBy = "typesHebergementCarhos")
    private List<HebergementCarhos> hebergementCarhos;

}
