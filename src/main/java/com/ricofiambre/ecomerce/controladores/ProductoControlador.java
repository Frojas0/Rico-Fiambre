package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.modelos.*;
import com.ricofiambre.ecomerce.repositorios.ProductoPesoRepositorio;
import com.ricofiambre.ecomerce.repositorios.ProductoUniRepositorio;
import com.ricofiambre.ecomerce.servicios.ClienteServicio;
import com.ricofiambre.ecomerce.servicios.ProductoPesoServicio;
import com.ricofiambre.ecomerce.servicios.ProductoUniServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProductoPesoServicio productoPesoServicio;
    @Autowired
    private ProductoUniServicio productoUniServicio;

    @PostMapping("/api/valorar-producto")
    public ResponseEntity<Object> valorarProducto(Authentication authentication,@RequestParam String nombre, @RequestParam double valor){

        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (cliente == null){
            return new ResponseEntity<>("Debes registrarte para valorar un producto", HttpStatus.FORBIDDEN);
        }
        if (nombre.isBlank()){
            return new ResponseEntity<>("Falta el nombre del producto", HttpStatus.FORBIDDEN);
        }
        if (valor < 0 || valor > 10){
            return new ResponseEntity<>("El puntaje debe estar entre 0 y 10", HttpStatus.FORBIDDEN);
        }

        String nombreProd = nombre.toUpperCase();

        ProductoUni productoUni = productoUniServicio.findByNombre(nombreProd);
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombreProd);

        if (productoUni != null){
            productoUni.addPuntuacion(valor);
            productoUniServicio.saveProductoUni(productoUni);
        } else if(productoPeso != null){
            productoPeso.addPuntuacion(valor);
            productoPesoServicio.saveProductoPeso(productoPeso);
        } else {
            return new ResponseEntity<>("Ese producto no existe", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Gracias por valorar el producto!", (HttpStatus.CREATED));
    }

    @PostMapping("/api/crear-producto")
    public ResponseEntity<Object> crearProductos(Authentication authentication,
                                                 @RequestParam String nombre,
                                                 @RequestParam String tipoProducto,
                                                 @RequestParam String descripcion,
                                                 @RequestParam double stock,
                                                 @RequestParam double precio,
                                                 @RequestParam String paisProducto,
                                                 @RequestParam boolean esPorPeso){

        if (!authentication.getName().equals("admin@admin.com")){
            return new ResponseEntity<>("No tienes permiso para crear productos", HttpStatus.FORBIDDEN);
        }
        if (productoPesoServicio.findByNombre(nombre) != null || productoUniServicio.findByNombre(nombre) != null){
            return new ResponseEntity<>("El producto a crear ya existe", HttpStatus.FORBIDDEN);
        }
        if (nombre.isBlank()) {
            return new ResponseEntity<>("Falta el nombre del producto", HttpStatus.FORBIDDEN);
        }
        if (tipoProducto.isBlank()) {
            return new ResponseEntity<>("Falta el tipo de producto", HttpStatus.FORBIDDEN);
        }
        if (descripcion.isBlank()) {
            return new ResponseEntity<>("Falta la descripcion del producto", HttpStatus.FORBIDDEN);
        }
        if (stock <= 0) {
            return new ResponseEntity<>("El stock no puede ser cero ni negativo", HttpStatus.FORBIDDEN);
        }
        if (precio <= 0) {
            return new ResponseEntity<>("El precio no puede ser 0 ni negativo", HttpStatus.FORBIDDEN);
        }
        if (paisProducto.isBlank()) {
            return new ResponseEntity<>("Falta el pais de proveniencia del producto", HttpStatus.FORBIDDEN);
        }

        String tipo = tipoProducto.toUpperCase();
        String pais = paisProducto.toUpperCase();
        String nombreMayuscula = nombre.toUpperCase();
        
        if (esPorPeso){
            ProductoPeso nuevoProductoPeso = (new ProductoPeso(nombreMayuscula, TipoProducto.valueOf(tipo), descripcion,stock,precio, PaisProducto.valueOf(pais), 5.0));
            productoPesoServicio.saveProductoPeso(nuevoProductoPeso);
        } else if (!esPorPeso){
            int stockInt = (int) stock;
            ProductoUni nuevoProductoUni = (new ProductoUni(nombreMayuscula, TipoProducto.valueOf(tipo), descripcion, stockInt,precio, PaisProducto.valueOf(pais), 5.0));
            productoUniServicio.saveProductoUni(nuevoProductoUni);
        } else {
            return new ResponseEntity<>("Tienes que especificar si es por Kg o por unidad", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}



