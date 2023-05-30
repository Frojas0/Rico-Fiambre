package com.ricofiambre.ecomerce.repositorios;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductoUniRepositorio extends JpaRepository<ProductoUni, Long> {
    ProductoUni findByNombre (String nombre);
}
