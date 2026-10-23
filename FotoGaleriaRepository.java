package com.lavajato.repository;

import com.lavajato.model.FotoGaleria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FotoGaleriaRepository extends JpaRepository<FotoGaleria, Long> {
    List<FotoGaleria> findByCategoria(String categoria);
}