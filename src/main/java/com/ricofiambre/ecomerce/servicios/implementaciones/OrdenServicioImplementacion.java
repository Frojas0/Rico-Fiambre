package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.dtos.OrdenDTO;
import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.repositorios.OrdenRepositorio;
import com.ricofiambre.ecomerce.servicios.OrdenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServicioImplementacion implements OrdenServicio {
    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Override
    public OrdenDTO getOrden(Long id) {
        Optional<Orden> optionalOrden = ordenRepositorio.findById(id);
        return optionalOrden.map(orden -> new OrdenDTO(orden)).orElse(null);
    }
    @Override
    public List<OrdenDTO> getOrdenes(){
        return ordenRepositorio.findAll().stream().map(orden -> new OrdenDTO(orden)).collect(Collectors.toList());
    }

    @Override
    public Orden findByNumeroDeOrden (String numeroDeOrden) {
        return ordenRepositorio.findByNumeroDeOrden(numeroDeOrden);
    }

    @Override
    public void saveNewOrden(Orden orden) {
        ordenRepositorio.save(orden);
    }

    @Override
    public void deleteOrden(Orden orden){
        ordenRepositorio.delete(orden);
    }
}
