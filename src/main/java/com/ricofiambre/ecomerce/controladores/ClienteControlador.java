package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.ClienteDTO;
import com.ricofiambre.ecomerce.dtos.ClienteRegistroDTO;
import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.repositorios.ClienteRepositorio;
import com.ricofiambre.ecomerce.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @PostMapping("/api/registrar-cliente")
    public ResponseEntity<Object> register(@RequestBody ClienteRegistroDTO clienteRegistroDTO) {
        if (clienteRegistroDTO.getNombre().isBlank()) {
            return new ResponseEntity<>("Falta el nombre", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getApellido().isBlank()) {
            return new ResponseEntity<>("Falta el apellido", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getEmail().isBlank()) {
            return new ResponseEntity<>("Falta el e-mail", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getContrasena().isBlank()) {
            return new ResponseEntity<>("Falta la contraseña", HttpStatus.FORBIDDEN);
        }
        if (clienteServicio.findByEmail(clienteRegistroDTO.getEmail()) != null) {
            return new ResponseEntity<>("El E-Mail ya está ne uso", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getDireccion().isBlank()) {
            return new ResponseEntity<>("Falta la dirección", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getCodPostal() <= 0) {
            return new ResponseEntity<>("Falta el código postal", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getTelefono().isBlank()) {
            return new ResponseEntity<>("Falta el teléfono", HttpStatus.FORBIDDEN);
        }
        if (clienteRegistroDTO.getCiudad().isBlank()) {
            return new ResponseEntity<>("Falta la ciudad", HttpStatus.FORBIDDEN);
        }

        Cliente nuevoCliente = new Cliente(clienteRegistroDTO.getNombre(),
                                clienteRegistroDTO.getApellido(),
                                clienteRegistroDTO.getEmail(),
                                clienteRegistroDTO.getDireccion(),
                                clienteRegistroDTO.getCiudad(),
                                clienteRegistroDTO.getCodPostal(),
                                clienteRegistroDTO.getTelefono(),
                                passwordEncoder.encode(clienteRegistroDTO.getContrasena()));
        clienteServicio.saveCliente(nuevoCliente);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
