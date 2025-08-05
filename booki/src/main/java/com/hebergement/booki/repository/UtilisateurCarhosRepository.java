package com.hebergement.booki.repository;

import com.hebergement.booki.model.UtilisateurCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurCarhosRepository extends JpaRepository <UtilisateurCarhos, Long>{

}



/** Une /interface/ en Java est un contrat : elle définit des méthodes que d'autres classes devront implémenter.
 *  Un /extends / en java défini un concept d'héritage **/