package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.dtos.ProductoPesoDTO;
import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;

import java.util.List;

public interface ProductoPesoServicio {
    void saveProductoPeso (ProductoPeso productoPeso);
    ProductoPeso findByNombre (String nombre);
    List<ProductoPesoDTO> getProductoPeso();
    void deleteProductoPeso(ProductoPeso productoPeso);
}
