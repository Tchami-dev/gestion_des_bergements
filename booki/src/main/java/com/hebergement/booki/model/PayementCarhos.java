package com.hebergement.booki.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="payement_carhos")
@Data
public class PayementCarhos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_paye")
    private  Long idPaye;

    @Column(name = "montant_paye")
    private  Long montantPaye;

    @Column(name = "date_paye")
    private LocalDateTime datePaye;

    @Column(name = "mode_paye")
    private String modePaye;

    @Column(name = "statut_paye")
    private  String statutPaye;


    /********** relation-de-multiplicité-entre-les-tables ***************/

/*** plusieurs payements peuvent être réalisées pour un même location ***/
    @ManyToOne
    @JoinColumn(name ="id_location")
    private  LocationCarhos locationCarhos;


}
