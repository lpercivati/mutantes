package com.example.demo.Repositories;

import com.example.demo.Entities.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantesRepository extends CrudRepository<Persona, String> {
    long countByIsMutant(boolean isMutant);
}
