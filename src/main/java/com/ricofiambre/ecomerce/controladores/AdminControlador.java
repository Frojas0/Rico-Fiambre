package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProductoPesoServicio productoPesoServicio;
    @Autowired
    private ProductoUniServicio productoUniServicio;
    @Autowired
    private OrdenServicio ordenServicio;
    @Autowired
    private OrdenProductoUniServicio ordenProductoUniServicio;
    @Autowired
    private OrdenProductoPesoServicio ordenProductoPesoServicio;


    @PostMapping("/api/crear-descuento-producto")
    public ResponseEntity<Object> valorarProducto(Authentication authentication,
                                                  @RequestParam String nombreProducto,
                                                  @RequestParam int valorDescuento) {
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombreProducto.toUpperCase());
        ProductoUni productoUni = productoUniServicio.findByNombre(nombreProducto.toUpperCase());
        if (!authentication.getName().equals("admin@admin.com")){
            return new ResponseEntity<>("No tienes permiso para modificar productos.", HttpStatus.FORBIDDEN);
        }
        if (nombreProducto.isBlank()){
            return new ResponseEntity<>("Tienes que seleccionar un producto a modificar.", HttpStatus.FORBIDDEN);
        }
        double descuento = (100 - valorDescuento) * 0.01;sw

        if (descuento > 1 || descuento <0){
            return new ResponseEntity<>("Tienes que elegir un porcentaje mayor a 0 y menor a 100% de descuento." +descuento , HttpStatus.FORBIDDEN);
        }
        if (productoPeso == null && productoUni == null){
            return new ResponseEntity<>("No se encontró el producto seleccionado.", HttpStatus.FORBIDDEN);
        }
        if (productoPeso != null){
            productoPeso.setDescuento(descuento);
            productoPesoServicio.saveProductoPeso(productoPeso);
        } else if (productoUni != null){
            productoUni.setDescuento(descuento);
            productoUniServicio.saveProductoUni(productoUni);
        }
        return new ResponseEntity<>("Creado: "+descuento, HttpStatus.CREATED);
    }
}

