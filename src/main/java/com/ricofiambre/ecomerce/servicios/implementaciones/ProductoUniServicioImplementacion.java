package com.ricofiambre.ecomerce.servicios.implementaciones;
import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.repositorios.ProductoUniRepositorio;
import com.ricofiambre.ecomerce.servicios.ProductoUniServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoUniServicioImplementacion implements ProductoUniServicio {
    @Autowired
    private ProductoUniRepositorio productoUniRepositorio;

    @Override
    public void saveProductoUni(ProductoUni productoUni) {
        productoUniRepositorio.save(productoUni);
    }
    @Override
    public ProductoUni findByNombre(String nombre){return productoUniRepositorio.findByNombre(nombre);}
    @Override
    public List<ProductoUniDTO> getProductoUni(){
        return productoUniRepositorio.findAll().stream().map(prod -> new ProductoUniDTO(prod)).collect(Collectors.toList());
    }
    @Override
    public void deleteProductoUni(ProductoUni productoUni){
        productoUniRepositorio.delete(productoUni);
    }
}
