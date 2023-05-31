package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import com.ricofiambre.ecomerce.modelos.OrdenProductoUni;
import com.ricofiambre.ecomerce.repositorios.OrdenProductoPesoRepositorio;
import com.ricofiambre.ecomerce.repositorios.OrdenProductoUniRepositorio;
import com.ricofiambre.ecomerce.servicios.OrdenProductoPesoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenProductoPesoImplementacion implements OrdenProductoPesoServicio {

    @Autowired
    private OrdenProductoPesoRepositorio ordenProductoPesoRepositorio;

    @Override
    public void saveOrdenProductoPeso(OrdenProductoPeso ordenProductoPeso) {
        ordenProductoPesoRepositorio.save(ordenProductoPeso);
    }
}
