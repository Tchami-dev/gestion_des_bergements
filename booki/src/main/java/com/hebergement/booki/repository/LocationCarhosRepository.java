package com.hebergement.booki.repository;

import com.hebergement.booki.model.LocationCarhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationCarhosRepository extends JpaRepository <LocationCarhos, Long> {


}
