package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.repositorios.ProductoPesoRepositorio;
import com.ricofiambre.ecomerce.servicios.ProductoPesoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoPesoServicioImplementacion implements ProductoPesoServicio {
    @Autowired
    private ProductoPesoRepositorio productoPesoRepositorio;

    @Override
    public void saveProductoPeso(ProductoPeso productoPeso) {
        productoPesoRepositorio.save(productoPeso);
    }
    @Override
    public ProductoPeso findByNombre(String nombre){return productoPesoRepositorio.findByNombre(nombre);}
}
