package com.hebergement.booki.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateur_carhos")
@Data
public class UtilisateurCarhos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(name = "nom_utilisateur")
    @NotBlank(message = "Merci de renseigner votre: Nom")
    private String nomUtilisateur;

    @Column(name = "prenom_utilisateur")
    @NotBlank(message = "Merci de renseigner votre: Prénom")
    private  String prenomUtilisateur;

    @Column(name = "num_tel_utilisateur")
    @NotBlank(message = "Merci de renseigner votre: Numéro de Téléphone")
    @Pattern(regexp = "^\\+237[\\s\\-]?[0-9]{9}$",
            message = "Numéro de téléphone invalide. Il doit commencer par +237 suivi de 9 chiffres.")

    private String numTelUtilisateur;

    @Column(name = "email_utilisateur")
    @NotBlank(message = "Merci de renseigner votre: boîte mail ")
    private  String emailUtilisateur;

    @Column(name = "num_cni_utilisateur")
    @NotBlank(message = "veillez renseigner: votre numéro de votre CNI ou de votre Passeport")
    private  String numCniUtilisateur;


    @Column(name = "mot_de_passe_utilisateur")
    @NotBlank(message = "Merci de renseigner: votre mot de passe")
    private  String motDePasseUtilisateur;

    @Column(name = "create_time")
//    @NotNull(message = "Merci de renseigner la date de création")
    private LocalDateTime createdAt;

    @Column(name = "update_time")
//    @NotNull(message = "Merci de renseigner la date de mise à jour")
    private  LocalDateTime updateAt;

/*************** relation-de-multiplicité-entre-les-tables *****************/

/** qui a effectuer une location **/
    @OneToMany (mappedBy = "utilisateurCarhos")  /***  idication de la multiplicite **/
     private  List<LocationCarhos> locationCarhos ;

     @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)

    private  RoleUtilisateurCarhos roleUtilisateurCarhos;


}
/******
 * pour l'attribut numTel
 * ^ : Le début de la chaîne.

 \\+237 : L'indicatif du Cameroun. Le \\+ échappe le symbole plus.

 [\\s\\-]? : Il peut y avoir un espace (\\s) ou un tiret (\\-) optionnel entre l'indicatif et les chiffres.

 [0-9]{9} : Ensuite, tu attends exactement 9 chiffres pour le numéro. *******/