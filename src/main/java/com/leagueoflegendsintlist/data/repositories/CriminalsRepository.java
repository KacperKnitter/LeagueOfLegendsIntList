package com.leagueoflegendsintlist.data.repositories;


import com.leagueoflegendsintlist.data.model.entities.Criminal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CriminalsRepository extends CrudRepository<Criminal,Long> {
    Optional<Criminal> findCriminalByCriminalId(Long id);

}
