package com.ricofiambre.ecomerce.repositorios;

import com.ricofiambre.ecomerce.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);
}
