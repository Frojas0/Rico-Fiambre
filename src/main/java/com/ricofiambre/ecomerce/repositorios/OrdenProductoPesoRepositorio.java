package com.ricofiambre.ecomerce.repositorios;

import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrdenProductoPesoRepositorio extends JpaRepository<OrdenProductoPeso, Long> {
}
