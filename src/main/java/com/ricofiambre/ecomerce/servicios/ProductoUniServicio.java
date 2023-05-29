package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;

public interface ProductoUniServicio {
    void saveProductoUni(ProductoUni productoUni);
    ProductoUni findByNombre (String nombre);

}
