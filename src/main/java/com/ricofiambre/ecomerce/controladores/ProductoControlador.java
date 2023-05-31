package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.CarritoCompraDTO;
import com.ricofiambre.ecomerce.dtos.ProductoPesoDTO;
import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.*;
import com.ricofiambre.ecomerce.servicios.*;
import com.ricofiambre.ecomerce.utilidades.NumerosUtilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

//    PAGAR UN PRODUCTO CON LA API DE HOMEBANKING
    //transactional
    //requestbody
    @PostMapping("/api/pagar-producto")
    public ResponseEntity<Object> pagarConTarjeta(@RequestParam int cvv,
                                                  @RequestParam double monto,
                                                  @RequestParam String numeroTarjeta,
                                                  @RequestParam String descripcion) {
        if (monto < 0){
            return new ResponseEntity<>("El monto no debe ser negativo", HttpStatus.FORBIDDEN);
        }
        if (descripcion.isBlank()){
            return new ResponseEntity<>("Debes proporcionar una descripcion", HttpStatus.FORBIDDEN);
        }
        if (numeroTarjeta.isBlank()){
            return new ResponseEntity<>("Debes proporcionar un numero de tarjeta", HttpStatus.FORBIDDEN);
        }
        if (numeroTarjeta.length() != 19){
            return new ResponseEntity<>("Debes proporcionar un numero de tarjeta valido", HttpStatus.FORBIDDEN);
        }

        try {
            URL url = new URL("https://mindhub-brothershomebanking-production.up.railway.app/api/card/payment");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String cuerpoDeSolicitud = "{\"cvv\": " + cvv + ", \"amount\": " + monto + ", \"number\": \"" + numeroTarjeta + "\", \"description\": \"" + descripcion + "\"}";
            connection.getOutputStream().write(cuerpoDeSolicitud.getBytes());

            int codigoDeRespuesta = connection.getResponseCode();
            if (codigoDeRespuesta == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                System.out.println("Respuesta del servidor: " + response);

                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago aceptado", HttpStatus.CREATED);
            } else {
                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago rechazado", HttpStatus.FORBIDDEN);
            }
        } catch (Exception err) {
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el pago");
        }
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

//    COMPRAR LOS PRODUCTOS DEL CARRITO DE COMPRA - CLIENTE

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

            ProductoPeso productoPeso = productoPesoServicio.findByNombre(partes[0]);
            ProductoUni productoUni = productoUniServicio.findByNombre(partes[0]);

            if (productoPeso != null) {
                double cantidadPrecio1 = productoPeso.getPrecio() * Double.parseDouble(partes[1]);
                totalCompra += cantidadPrecio1;

                if (productoPeso.getStock()-Double.parseDouble(partes[1]) < 0){
                    ordenServicio.deleteOrden(orden);
                    return new ResponseEntity<>("No hay suficiente stock de: " + partes[0], HttpStatus.FORBIDDEN);
                }

                OrdenProductoPeso ordenProductoPeso = (new OrdenProductoPeso(Double.parseDouble(partes[1]), cantidadPrecio1));
                productoPeso.setStock(productoPeso.getStock()-Double.parseDouble(partes[1]));
                orden.addOrdenProductoPeso(ordenProductoPeso);
                productoPeso.addOrdenProductoPeso(ordenProductoPeso);
                ordenProductoPesoServicio.saveOrdenProductoPeso(ordenProductoPeso);

            } else if (productoUni != null) {
                double cantidadPrecio = productoUni.getPrecio() * Integer.parseInt(partes[1]);
                totalCompra += cantidadPrecio;

                if (productoUni.getStock()-Integer.parseInt(partes[1]) < 0){
                    ordenServicio.deleteOrden(orden);
                    return new ResponseEntity<>("No hay suficiente stock de: " + partes[0], HttpStatus.FORBIDDEN);
                }

                OrdenProductoUni ordenProductoUni = (new OrdenProductoUni(Integer.parseInt(partes[1]), cantidadPrecio));
                productoUni.setStock(productoUni.getStock()-Integer.parseInt(partes[1]));
                orden.addOrdenProductoUni(ordenProductoUni);
                productoUni.addOrdenProductoUni(ordenProductoUni);
                ordenProductoUniServicio.saveOrdenProductoUni(ordenProductoUni);

            }
        }
        orden.setTotal(totalCompra);
        ordenServicio.saveNewOrden(orden);
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



