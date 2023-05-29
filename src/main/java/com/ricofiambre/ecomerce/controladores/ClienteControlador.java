package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.ClienteDTO;
import com.ricofiambre.ecomerce.repositorios.ClienteRepositorio;
import com.ricofiambre.ecomerce.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/api/clientes")
    public List<ClienteDTO> getCliente() {
        return clienteServicio.getCliente();
    }

    @GetMapping("/api/clientes/{id}")
    public ClienteDTO getCliente(@PathVariable Long id){
        return clienteServicio.getClienteDTO(id);
    }

    @GetMapping("/api/clientes/actual")
    public ClienteDTO getClienteActual(Authentication authentication) {
        return clienteServicio.getClienteActual(authentication);
    }

}
