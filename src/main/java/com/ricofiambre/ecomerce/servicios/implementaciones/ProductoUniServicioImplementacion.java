package com.ricofiambre.ecomerce.servicios.implementaciones;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.repositorios.ProductoUniRepositorio;
import com.ricofiambre.ecomerce.servicios.ProductoUniServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
