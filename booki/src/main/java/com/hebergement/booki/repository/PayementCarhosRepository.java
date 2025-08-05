package com.hebergement.booki.repository;

import com.hebergement.booki.model.PayementCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayementCarhosRepository extends JpaRepository<PayementCarhos, Long> {
}
