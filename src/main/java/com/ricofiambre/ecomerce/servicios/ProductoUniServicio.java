package com.ricofiambre.ecomerce.servicios;

import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;

import java.util.List;

public interface ProductoUniServicio {
    void saveProductoUni(ProductoUni productoUni);
    ProductoUni findByNombre (String nombre);
    List<ProductoUniDTO> getProductoUni();
    void deleteProductoUni(ProductoUni productoUni);
}
