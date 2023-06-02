package com.ricofiambre.ecomerce.repositorios;

import com.ricofiambre.ecomerce.modelos.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrdenRepositorio extends JpaRepository<Orden, Long> {
    Orden findByNumeroDeOrden(String numeroDeOrden);
}
