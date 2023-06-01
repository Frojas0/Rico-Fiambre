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
        return productoPesoServicio.getProductoPeso();
    }
    @GetMapping("/api/productoUni")
    public List<ProductoUniDTO> getProductoUni() {
        return productoUniServicio.getProductoUni();
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
//                    ordenServicio.deleteOrden(orden);
//                    return new ResponseEntity<>("No hay suficiente stock de: " + partes[0], HttpStatus.FORBIDDEN);
                    throw new RuntimeException("No hay suficiente stock de: " + parteNombre);
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
//                    ordenServicio.deleteOrden(orden);
//                    return new ResponseEntity<>("No hay suficiente stock de: " + partes[0], HttpStatus.FORBIDDEN);
                    throw new RuntimeException("No hay suficiente stock de: " + parteNombre);
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
//            return new ResponseEntity<>("Sucedió un error al realizar el pago.", HttpStatus.FORBIDDEN);
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


//    CREAR UN NUEVO PRODUCTO - ADMIN
    @PostMapping("/api/crear-producto")
    public ResponseEntity<Object> crearProductos(Authentication authentication,
                                                 @RequestParam String nombre,
                                                 @RequestParam String tipoProducto,
                                                 @RequestParam String descripcion,
                                                 @RequestParam double stock,
                                                 @RequestParam double precio,
                                                 @RequestParam String paisProducto,
                                                 @RequestParam boolean esPorPeso,
                                                 @RequestParam String url){

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
        if (url.isBlank()){
            return new ResponseEntity<>("Debes proporcionar la URL de la imagen", HttpStatus.FORBIDDEN);
        }

        String tipo = tipoProducto.toUpperCase();
        String pais = paisProducto.toUpperCase();
        String nombreMayuscula = nombre.toUpperCase();
        
        if (esPorPeso){
            ProductoPeso nuevoProductoPeso = (new ProductoPeso(nombreMayuscula, TipoProducto.valueOf(tipo), descripcion,stock,precio, PaisProducto.valueOf(pais), 5.0,url));
            productoPesoServicio.saveProductoPeso(nuevoProductoPeso);
        } else if (!esPorPeso){
            int stockInt = (int) stock;
            ProductoUni nuevoProductoUni = (new ProductoUni(nombreMayuscula, TipoProducto.valueOf(tipo), descripcion, stockInt,precio, PaisProducto.valueOf(pais), 5.0,url));
            productoUniServicio.saveProductoUni(nuevoProductoUni);
        } else {
            return new ResponseEntity<>("Tienes que especificar si es por Kg o por unidad", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



//    MODIFICAR EL STOCK - ADMIN
    @PostMapping("/api/modificar-stock")
    public ResponseEntity<Object> modificarStock(Authentication authentication,
                                                 @RequestParam String nombreProducto,
                                                 @RequestParam double cantidad) {
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (!cliente.getEmail().equals("admin@admin.com")){
            return new ResponseEntity<>("Esta acción debe ser realizada por un administrador", HttpStatus.FORBIDDEN);
        }
        if (nombreProducto.isBlank()){
            return new ResponseEntity<>("Falta el nombre", HttpStatus.FORBIDDEN);
        }

        String nombre = nombreProducto.toUpperCase();
        ProductoUni productoUni = productoUniServicio.findByNombre(nombre);
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombre);

        if (productoUni != null){
            if (productoUni.getStock() + cantidad < 0){
                return new ResponseEntity<>("La cantidad a restar es mayor que el stock del producto", HttpStatus.FORBIDDEN);
            }
            productoUni.setStock((int) (productoUni.getStock()+cantidad));
            productoUniServicio.saveProductoUni(productoUni);

        }else if(productoPeso != null){
            if (productoPeso.getStock() + cantidad < 0){
                return new ResponseEntity<>("La cantidad a restar es mayor que el stock del producto", HttpStatus.FORBIDDEN);
            }
            productoPeso.setStock(productoPeso.getStock()+cantidad);
            productoPesoServicio.saveProductoPeso(productoPeso);

        }else{
            return new ResponseEntity<>("El producto "+nombreProducto+" no existe", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Stock actualizado", (HttpStatus.CREATED));
    }
}



