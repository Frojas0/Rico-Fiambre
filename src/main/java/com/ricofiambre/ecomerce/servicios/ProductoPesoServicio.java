package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;

public interface ProductoPesoServicio {
    void saveProductoPeso (ProductoPeso productoPeso);
    ProductoPeso findByNombre (String nombre);
}
