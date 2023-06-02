package com.ricofiambre.ecomerce.repositorios;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductoPesoRepositorio extends JpaRepository<ProductoPeso, Long> {
    ProductoPeso findByNombre (String nombre);
}
