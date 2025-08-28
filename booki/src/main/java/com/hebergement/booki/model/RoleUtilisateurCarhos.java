package com.hebergement.booki.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "role_utilisateur_carhos")
@Data
public class RoleUtilisateurCarhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;


    @NotNull(message="Merci de renseigner votre statut administratif" )
    @Column (name = "nom_role")
    private  String nomRole;

    @NotNull(message = "Merci de décrire les fonctions de ce statut")
    @Column(name = "description_role")
    private String descriptionRole;


    /** niveau d'accès par utilisateur ***/


    /***************** relation-de-multiplicité-entre-les-tables **********************/

    @OneToMany(mappedBy ="roleUtilisateurCarhos" )
    private List<UtilisateurCarhos> utilisateurCarhos;

}
