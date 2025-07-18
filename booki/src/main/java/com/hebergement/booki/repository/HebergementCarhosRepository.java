package com.hebergement.booki.repository;

import com.hebergement.booki.model.HebergementCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HebergementCarhosRepository extends JpaRepository<HebergementCarhos, Long> {

}