package com.hebergement.booki.repository;

import com.hebergement.booki.model.HebergementCarhos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HebergementCarhosRepository extends JpaRepository<HebergementCarhos, Long> {

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:keyword IS NULL OR LOWER(h.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(h.hebergementCarhosType != 'VIP' OR h.nbreEtoile < 4)")
    Page<HebergementCarhos> findByNomContainingIgnoreCaseAndHebergementCarhosTypeNotVipOrNbreEtoileLessThan(
            @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(h.hebergementCarhosType != 'VIP' OR h.nbreEtoile < 4)")
    Page<HebergementCarhos> findByHebergementCarhosTypeNotVipOrNbreEtoileLessThan(Pageable pageable);

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(:keyword IS NULL OR LOWER(h.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(h.hebergementCarhosType = 'VIP' AND h.nbreEtoile >= 4)")
    Page<HebergementCarhos> findByNomContainingIgnoreCaseAndHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(
            @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT h FROM HebergementCarhos h WHERE " +
            "(h.hebergementCarhosType = 'VIP' AND h.nbreEtoile >= 4)")
    Page<HebergementCarhos> findByHebergementCarhosTypeVipAndNbreEtoileGreaterOrEqual(Pageable pageable);
}
