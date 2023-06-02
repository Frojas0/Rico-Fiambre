package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.dtos.OrdenDTO;
import com.ricofiambre.ecomerce.modelos.Orden;

import java.util.List;

public interface OrdenServicio {
    OrdenDTO getOrden(Long id);
    List<OrdenDTO> getOrdenes();
    Orden findByNumeroDeOrden(String numeroDeOrden);
    void saveNewOrden(Orden orden);
    void deleteOrden(Orden orden);
}
