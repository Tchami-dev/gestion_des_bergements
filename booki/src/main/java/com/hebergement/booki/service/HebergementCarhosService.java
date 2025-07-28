package com.hebergement.booki.service;


import com.hebergement.booki.config.HebergementCarhosRessource;
import com.hebergement.booki.model.HebergementCarhos;
import com.hebergement.booki.repository.HebergementCarhosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*@Service
@RequiredArgsConstructor
public class HebergementCarhosService {

    private final HebergementCarhosRepository hebergementCarhosRepository;

    public Page<HebergementCarhos>getall(
            int page,
            int size,
            String sortBy,
            String sortOrder
    ){
        var sort = sortOrder.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);
        return hebergementCarhosRepository.findAll(pageable);
    }

}*/
