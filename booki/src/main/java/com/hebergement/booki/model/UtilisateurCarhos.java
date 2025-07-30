package com.hebergement.booki.model;


import jakarta.persistence.*;
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
    private String nomUtilisateur;

    @Column(name = "prenom_utilisateur")
    private  String prenomUtilisateur;

    @Column(name = "num_tel_utilisateur")
    private String numTelUtilisateur;

    @Column(name = "email_utilisateur")
    private  String emailUtilisateur;

    @Column(name = "num_cni_utilisateur")
    private  String numCniUtilisateur;

    @Column(name = "mot_de_passe_utilisateur")
    private  String motDePasseUtilisateur;

    @Column(name = "create_time")
    private LocalDateTime createdAt;

    @Column(name = "update_time")
    private  LocalDateTime updateAt;

/*************** relation-de-multiplicité-entre-les-tables *****************/

/** qui a effectuer une location **/
    @OneToMany (mappedBy = "utilisateurCarhos")  /***  idication de la multiplicite **/
     private  List<LocationCarhos> locationCarhos ;

     @ManyToOne
    @JoinColumn(name = "id_role")
    private  RoleUtilisateurCarhos roleUtilisateurCarhos;









}
