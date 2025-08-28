package com.hebergement.booki.repository;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HebergementCarhosRepository extends JpaRepository<HebergementCarhos, Long> {
    List<HebergementCarhos> findByNomContainingIgnoreCase(String keyword);

//    Page<HebergementCarhos> findAll(Pageable pageable);
    //commit
}
