package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.dtos.ClienteDTO;
import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.repositorios.ClienteRepositorio;
import com.ricofiambre.ecomerce.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServicioImplementacion implements ClienteServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Override
    public List<ClienteDTO> getCliente() {
        return clienteRepositorio.findAll().stream().map(client -> new ClienteDTO(client)).collect(Collectors.toList());
    }
    @Override
    public ClienteDTO getClienteDTO(Long id) {
        return new ClienteDTO(clienteRepositorio.findById(id).orElse(null));
    }
    @Override
    public ClienteDTO getClienteActual(Authentication authentication) {
        return new ClienteDTO(clienteRepositorio.findByEmail(authentication.getName()));
    }
    @Override
    public Cliente findByEmail(String email) {
        return clienteRepositorio.findByEmail(email);
    }
    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

}
