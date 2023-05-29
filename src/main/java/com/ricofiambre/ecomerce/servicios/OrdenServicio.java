package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.dtos.OrdenDTO;

import java.util.List;

public interface OrdenServicio {
    OrdenDTO getOrden(Long id);
    List<OrdenDTO> getOrdenes();
}
