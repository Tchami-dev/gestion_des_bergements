package com.hebergement.booki.repository;

import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.model.HebergementCarhosSpecificite;
import com.hebergement.booki.model.HebergementCarhosStatut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HebergementCarhosRepository extends JpaRepository<HebergementCarhos, Long> {

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:keyword IS NULL OR LOWER(h.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:filtre IS NULL OR h.hebergementCarhosSpecificite = :filtre) AND " +
            "(h.hebergementCarhosType != 'VIP' OR h.nbreEtoile < 4)")
    Page<HebergementCarhos> findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
            @Param("keyword") String keyword,
            @Param("filtre") HebergementCarhosSpecificite filtre,
            Pageable pageable);


    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:filtre IS NULL OR h.hebergementCarhosSpecificite = :filtre) AND "+
            "(h.hebergementCarhosType != 'VIP' OR h.nbreEtoile < 4)")
    Page<HebergementCarhos> findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
            @Param("filtre") HebergementCarhosSpecificite filtre,
            Pageable pageable);

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:keyword IS NULL OR LOWER(h.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:filtre IS NULL OR h.hebergementCarhosSpecificite = :filtre) AND " +
            "(h.hebergementCarhosType = 'VIP' AND h.nbreEtoile >= 4)")
    Page<HebergementCarhos> findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
            @Param("keyword") String keyword,
            @Param("filtre") HebergementCarhosSpecificite filtre,
            Pageable pageable);

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:filtre IS NULL OR h.hebergementCarhosSpecificite = :filtre) AND " +
            "(h.hebergementCarhosType = 'VIP' AND h.nbreEtoile >= 4)")
    Page<HebergementCarhos> findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
            @Param("filtre") HebergementCarhosSpecificite filtre,
            Pageable pageable);

    long count(); // pour cmpter tout les total

    long countByHebergementCarhosStatut(HebergementCarhosStatut hebergementCarhosStatut);
}
