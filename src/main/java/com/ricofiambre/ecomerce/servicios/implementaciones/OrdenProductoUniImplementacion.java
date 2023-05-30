package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.modelos.OrdenProductoUni;
import com.ricofiambre.ecomerce.repositorios.OrdenProductoUniRepositorio;
import com.ricofiambre.ecomerce.servicios.OrdenProductoUniServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenProductoUniImplementacion implements OrdenProductoUniServicio {

    @Autowired
    private OrdenProductoUniRepositorio ordenProductoUniRepositorio;

    @Override
    public void saveOrdenProductoUni(OrdenProductoUni ordenProductoUni) {
        ordenProductoUniRepositorio.save(ordenProductoUni);
    }
}
