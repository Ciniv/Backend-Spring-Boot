package com.example.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.entities.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    
    @Query("Select c from Pais c where lower(c.nome) like lower(concat('%', concat(:text, '%')))")
    List<Pais> findByText(String text);

}
