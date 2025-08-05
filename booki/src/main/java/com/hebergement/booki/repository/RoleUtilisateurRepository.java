package com.hebergement.booki.repository;

import com.hebergement.booki.model.RoleUtilisateurCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUtilisateurRepository extends JpaRepository<RoleUtilisateurCarhos, Long> {

}
