package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.CarritoCompraDTO;
import com.ricofiambre.ecomerce.dtos.ProductoPesoDTO;
import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.*;
import com.ricofiambre.ecomerce.servicios.*;
import com.ricofiambre.ecomerce.utilidades.NumerosUtilidad;
import com.ricofiambre.ecomerce.utilidades.PagarConTarjetaUtilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoControlador {
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


    @GetMapping("/api/productoPeso")
    public List<ProductoPesoDTO> getProductoPeso() {
        return productoPesoServicio.getProductoPeso().stream().filter(productoPeso -> productoPeso.getEstaActivo()).collect(Collectors.toList());
    }
    @GetMapping("/api/productoUni")
    public List<ProductoUniDTO> getProductoUni() {
        return productoUniServicio.getProductoUni().stream().filter(productoUni -> productoUni.getEstaActivo()).collect(Collectors.toList());
    }

    //    COMPRAR LOS PRODUCTOS DEL CARRITO DE COMPRA - CLIENTE

    @Transactional
    @PostMapping("/api/carrito-compra")
    public ResponseEntity<Object> compraProducto(Authentication authentication, @RequestBody CarritoCompraDTO carritoCompraDTO){

        Cliente cliente = clienteServicio.findByEmail(authentication.getName());
        String numero;

        do {
            numero = NumerosUtilidad.getNumero();
        } while (ordenServicio.findByNumeroDeOrden(numero) != null);

        if (cliente == null){
            return new ResponseEntity<>("Tienes que estar logueado para realizar esta acción", HttpStatus.FORBIDDEN);
        }
        if (carritoCompraDTO.getProductos().size() == 0){
            return new ResponseEntity<>("Tienes que agregar productos al carrito", HttpStatus.FORBIDDEN);
        }

        Double totalCompra = 0.0;

        Orden orden = (new Orden(LocalDateTime.now(), false, true, totalCompra, numero));
        cliente.addOrden(orden);
        ordenServicio.saveNewOrden(orden);

        for (int i = 0; i < carritoCompraDTO.getProductos().size(); i++) {
            String entrada = carritoCompraDTO.getProductos().get(i);
            String[] partes = entrada.split("-");
            String parteNombre = partes[0].toUpperCase();
            String parteCantidad = partes[1];

            ProductoPeso productoPeso = productoPesoServicio.findByNombre(parteNombre);
            ProductoUni productoUni = productoUniServicio.findByNombre(parteNombre);

            if (productoUni == null && productoPeso == null){
                throw new RuntimeException("No existe un produto: " + parteNombre);
            }
            if (productoPeso != null) {
                double cantidadPrecio1 = productoPeso.getPrecio() * productoPeso.getDescuento() * Double.parseDouble(parteCantidad);
                totalCompra += cantidadPrecio1;

                if (productoPeso.getStock()-Double.parseDouble(parteCantidad) < 0){
                    throw new RuntimeException("No hay suficiente stock de: " + parteNombre);
                }
                if (!productoPeso.getEstaActivo()){
                    throw new RuntimeException("Este producto ha sido eliminado: " + parteNombre);
                }

                OrdenProductoPeso ordenProductoPeso = (new OrdenProductoPeso(Double.parseDouble(parteCantidad), cantidadPrecio1));
                productoPeso.setStock(productoPeso.getStock()-Double.parseDouble(parteCantidad));
                orden.addOrdenProductoPeso(ordenProductoPeso);
                productoPeso.addOrdenProductoPeso(ordenProductoPeso);
                ordenProductoPesoServicio.saveOrdenProductoPeso(ordenProductoPeso);

            } else if (productoUni != null) {
                double cantidadPrecio = productoUni.getPrecio() * productoUni.getDescuento() * Integer.parseInt(parteCantidad);
                totalCompra += cantidadPrecio;

                if (productoUni.getStock()-Integer.parseInt(parteCantidad) < 0){
                    throw new RuntimeException("No hay suficiente stock de: " + parteNombre);
                }
                if (!productoUni.getEstaActivo()){
                    throw new RuntimeException("Este producto ha sido eliminado: " + parteNombre);
                }
                OrdenProductoUni ordenProductoUni = (new OrdenProductoUni(Integer.parseInt(parteCantidad), cantidadPrecio));
                productoUni.setStock(productoUni.getStock()-Integer.parseInt(parteCantidad));
                orden.addOrdenProductoUni(ordenProductoUni);
                productoUni.addOrdenProductoUni(ordenProductoUni);
                ordenProductoUniServicio.saveOrdenProductoUni(ordenProductoUni);

            }
        }

        ResponseEntity<Object> pagarConTarjeta = PagarConTarjetaUtilidad.pagarConTarjeta(carritoCompraDTO, totalCompra);

        if (pagarConTarjeta.getStatusCode() == HttpStatus.CREATED){
            orden.setTotal(totalCompra);
            ordenServicio.saveNewOrden(orden);
        } else {
            throw new RuntimeException("Se produjo un error");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


//    VALORAR UN PRODUCTO - CLIENTE
    @PostMapping("/api/valorar-producto")
    public ResponseEntity<Object> valorarProducto(Authentication authentication,
                                                  @RequestParam String nombre,
                                                  @RequestParam double valor){

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

        if (productoUni != null && productoUni.getEstaActivo()){
            productoUni.addPuntuacion(valor);
            productoUniServicio.saveProductoUni(productoUni);
        } else if(productoPeso != null && productoPeso.getEstaActivo()){
            productoPeso.addPuntuacion(valor);
            productoPesoServicio.saveProductoPeso(productoPeso);
        } else {
            return new ResponseEntity<>("Ese producto no existe, o ha sido eliminado.", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Gracias por valorar el producto!", (HttpStatus.CREATED));
    }
}



