package com.hebergement.booki.repository;

import com.hebergement.booki.model.LocationCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCarhosRepository extends JpaRepository <LocationCarhos, Long> {

}
