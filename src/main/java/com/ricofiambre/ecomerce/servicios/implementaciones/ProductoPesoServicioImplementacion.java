package com.ricofiambre.ecomerce.servicios.implementaciones;

import com.ricofiambre.ecomerce.dtos.ProductoPesoDTO;
import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.repositorios.ProductoPesoRepositorio;
import com.ricofiambre.ecomerce.servicios.ProductoPesoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<ProductoPesoDTO> getProductoPeso(){
        return productoPesoRepositorio.findAll().stream().map(prod -> new ProductoPesoDTO(prod)).collect(Collectors.toList());
    }
    @Override
    public void deleteProductoPeso(ProductoPeso productoPeso){
       productoPesoRepositorio.delete(productoPeso);
    }
}
