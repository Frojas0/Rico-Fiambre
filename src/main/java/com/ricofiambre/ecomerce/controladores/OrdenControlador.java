package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.OrdenDTO;
import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.servicios.ClienteServicio;
import com.ricofiambre.ecomerce.servicios.OrdenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrdenControlador {
    @Autowired
    private OrdenServicio ordenServicio;
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/api/ordenes")
        public List<OrdenDTO> getOrdenes(){return ordenServicio.getOrdenes();}

    @GetMapping("/api/ordenes-cliente")
    public List<OrdenDTO> getHistorialOrdenes(Authentication authentication){
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());
        List<OrdenDTO> ordenesCliente = cliente.getOrdenes().stream().map(ordenes -> new OrdenDTO(ordenes)).collect(Collectors.toList());
        return ordenesCliente;
    }
//    @GetMapping("/api/ticket-compra")
//    public


//    @GetMapping("/api/ordenes-cliente")
//    public String getHistorialOrdenes(Authentication authentication){
//        Cliente cliente = clienteServicio.findByEmail(authentication.getName());
//        List<OrdenDTO> ordenesCliente = cliente.getOrdenes().stream().map(ordenes -> new OrdenDTO(ordenes)).collect(Collectors.toList());
//        return ;
//    }
}
