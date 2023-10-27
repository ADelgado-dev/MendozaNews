package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.entidades.Portada;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortadaRepositorio extends JpaRepository<Portada, Long> {
    Optional<Portada> findById(Long id);
}