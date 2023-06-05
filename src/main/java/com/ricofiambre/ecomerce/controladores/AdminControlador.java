package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.ModificarProductoDTO;
import com.ricofiambre.ecomerce.dtos.ProductoPesoDTO;
import com.ricofiambre.ecomerce.dtos.ProductoUniDTO;
import com.ricofiambre.ecomerce.modelos.*;
import com.ricofiambre.ecomerce.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class AdminControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProductoPesoServicio productoPesoServicio;
    @Autowired
    private ProductoUniServicio productoUniServicio;

    //OBTENER PRODUCTOS ACTIVOS

    @GetMapping("/api/productos-activos")
    public ResponseEntity<Object> getProductosActivos(Authentication authentication){
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (!cliente.getEmail().equals("admin@admin.com")) {
            return new ResponseEntity<>("Debes ser un administrador!", HttpStatus.FORBIDDEN);
        }

        List <ProductoPesoDTO> productoPesos = productoPesoServicio.getProductoPeso().stream().filter(productoPeso -> productoPeso.getEstaActivo()).collect(Collectors.toList());
        List < ProductoUniDTO> productounis = productoUniServicio.getProductoUni().stream().filter(productoUni -> productoUni.getEstaActivo()).collect(Collectors.toList());

        List<Object> combinacion = new ArrayList<>();
        combinacion.addAll(productoPesos);
        combinacion.addAll(productounis);

        return new ResponseEntity<>(combinacion, HttpStatus.CREATED);
    }
    //OBTENER PRODUCTOS INACTIVOS
    @GetMapping("/api/productos-inactivos")
    public ResponseEntity<Object> getProductosInactivos(Authentication authentication){
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (!cliente.getEmail().equals("admin@admin.com")) {
            return new ResponseEntity<>("Debes ser un administrador!", HttpStatus.FORBIDDEN);
        }

        List <ProductoPesoDTO> productoPesos = productoPesoServicio.getProductoPeso().stream().filter(productoPeso -> productoPeso.getEstaActivo() == false).collect(Collectors.toList());
        List < ProductoUniDTO> productounis = productoUniServicio.getProductoUni().stream().filter(productoUni -> productoUni.getEstaActivo() == false).collect(Collectors.toList());

        List<Object> combinacion = new ArrayList<>();
        combinacion.addAll(productoPesos);
        combinacion.addAll(productounis);

        return new ResponseEntity<>(combinacion, HttpStatus.CREATED);
    }

    //CREAR DESCUENTO EN PRODUCTO
    @PostMapping("/api/crear-descuento-producto")
    public ResponseEntity<Object> descuentoProducto(Authentication authentication,
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
        double descuento = (100 - valorDescuento) * 0.01;

        if (descuento > 1 || descuento <0){
            return new ResponseEntity<>("Tienes que elegir un porcentaje mayor a 0 y menor a 100% de descuento." +descuento , HttpStatus.FORBIDDEN);
        }
        if (productoPeso == null && productoUni == null){
            return new ResponseEntity<>("No se encontró el producto seleccionado.", HttpStatus.FORBIDDEN);
        }
        if (productoPeso != null && productoPeso.getEstaActivo()){
            productoPeso.setDescuento(descuento);
            productoPesoServicio.saveProductoPeso(productoPeso);
        } else if (productoUni != null && productoUni.getEstaActivo()){
            productoUni.setDescuento(descuento);
            productoUniServicio.saveProductoUni(productoUni);
        }
        return new ResponseEntity<>("Creado: "+descuento, HttpStatus.CREATED);
    }

    //CREAR UN NUEVO PRODUCTO
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
        if (productoPesoServicio.findByNombre(nombre) != null || productoUniServicio.findByNombre(nombre) != null) {
            if (!productoPesoServicio.findByNombre(nombre).getEstaActivo() || !productoUniServicio.findByNombre(nombre).getEstaActivo()) {
                return new ResponseEntity<>("El producto a crear ya existe, y el mismo esta inactivo. Pruebe activandolo nuevamente.", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>("El producto a crear ya existe", HttpStatus.FORBIDDEN);
            }
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
            return new ResponseEntity<>("Falta la proveniencia del producto", HttpStatus.FORBIDDEN);
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

    //MODIFICAR PRODUCTO
    @PostMapping("/api/modificar-producto")
    public ResponseEntity<Object> modificarProducto(Authentication authentication,
                                                    @RequestBody ModificarProductoDTO modificarProductoDTO) {

        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        String nombre = modificarProductoDTO.getNombre().toUpperCase();
        String nuevoNombre = modificarProductoDTO.getNuevoNombre().toUpperCase();
        String descripcion = modificarProductoDTO.getDescripcion();
        String stock = modificarProductoDTO.getStock();
        String precio = modificarProductoDTO.getPrecio();
        String url = modificarProductoDTO.getUrl();

        TipoProducto tipo = null;
        if (!modificarProductoDTO.getTipo().isBlank()){

            try {
                tipo = TipoProducto.valueOf(modificarProductoDTO.getTipo().toUpperCase());
            }catch (Exception err){
                return new ResponseEntity<>("El Tipo proporcionado no existe en la base de datos", HttpStatus.FORBIDDEN);
            }
        }

        PaisProducto pais = null;
        if (!modificarProductoDTO.getOrigen().isBlank()) {
            try {
                pais = PaisProducto.valueOf(modificarProductoDTO.getOrigen().toUpperCase());
            }catch (Exception err){
                return new ResponseEntity<>("El Origen proporcionado no existe en la base de datos", HttpStatus.FORBIDDEN);
            }
        }

        if (!cliente.getEmail().equals("admin@admin.com")){
            return new ResponseEntity<>("Esta acción debe ser realizada por un administrador", HttpStatus.FORBIDDEN);
        }
        if (nombre.isBlank()){
            return new ResponseEntity<>("Debe proporcionar un nombre para buscar en la base de datos", HttpStatus.FORBIDDEN);
        }

        ProductoUni productoUni = productoUniServicio.findByNombre(nombre);
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombre);

        ProductoUni productoUniNuevo = productoUniServicio.findByNombre(nuevoNombre);
        ProductoPeso productoPesonuevo = productoPesoServicio.findByNombre(nuevoNombre);

        if (productoUniNuevo != null || productoPesonuevo != null){
            if (!productoUniNuevo.getEstaActivo() || !productoPesonuevo.getEstaActivo()){
                return new ResponseEntity<>("Ya existe un producto con el nombre "+nuevoNombre+". Esta eliminado", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Ya existe un producto con el nombre "+nuevoNombre, HttpStatus.FORBIDDEN);
        }

        //producto por unidad
        if (productoUni != null && productoUni.getEstaActivo()){
            if (nuevoNombre != null && !nuevoNombre.isBlank()){
                productoUni.setNombre(nuevoNombre);
                productoUniServicio.saveProductoUni(productoUni);
            }
            if (tipo != null ){
                productoUni.setTipo(tipo);
                productoUniServicio.saveProductoUni(productoUni);
            }
            if (descripcion != null && !descripcion.isBlank()){
                productoUni.setDescripcion(descripcion);
                productoUniServicio.saveProductoUni(productoUni);
            }
            if (stock != null && !stock.isBlank()){
                try{
                    int stock1 = Integer.parseInt(stock);
                    productoUni.setStock(stock1);
                    productoUniServicio.saveProductoUni(productoUni);
                } catch (Exception err) {
                    return new ResponseEntity<>("Debes proporcionar un stock numerico valido", HttpStatus.FORBIDDEN);
                }
            }
            if (precio != null && !precio.isBlank()) {
                try{
                    double precio1 = Double.parseDouble(precio);
                    productoUni.setPrecio(precio1);
                    productoUniServicio.saveProductoUni(productoUni);
                } catch (Exception err) {
                    return new ResponseEntity<>("Debes proporcionar un precio numerico valido", HttpStatus.FORBIDDEN);
                }
            }
            if ( pais != null ) {
                productoUni.setOrigen(pais);
                productoUniServicio.saveProductoUni(productoUni);
            }
            if (url != null && !url.isBlank()){
                productoUni.setUrl(url);
                productoUniServicio.saveProductoUni(productoUni);
            }

            //producto por peso
        }else if(productoPeso != null && productoPeso.getEstaActivo()){
            if (nuevoNombre != null && !nuevoNombre.isBlank()){
                productoPeso.setNombre(nuevoNombre);
                productoPesoServicio.saveProductoPeso(productoPeso);
            }
            if (tipo != null ){
                productoPeso.setTipo(tipo);
                productoPesoServicio.saveProductoPeso(productoPeso);
            }
            if (descripcion != null && !descripcion.isBlank()){
                productoPeso.setTipo(tipo);
                productoPesoServicio.saveProductoPeso(productoPeso);
            }
            if (stock != null && !stock.isBlank()){
                try{
                    productoPeso.setStock(Double.parseDouble(stock));
                    productoPesoServicio.saveProductoPeso(productoPeso);
                }catch (Exception err){
                    return new ResponseEntity<>("Debes proporcionar un stock numerico valido", HttpStatus.FORBIDDEN);
                }
            }
            if (precio != null && !precio.isBlank()){
                try{
                    productoPeso.setPrecio(Double.parseDouble(precio));
                    productoPesoServicio.saveProductoPeso(productoPeso);
                }catch (Exception err){
                    return new ResponseEntity<>("Debes proporcionar un stock numerico valido", HttpStatus.FORBIDDEN);
                }
            }
            if (pais != null ){
                productoPeso.setOrigen(pais);
                productoPesoServicio.saveProductoPeso(productoPeso);
            }
            if (url != null && !url.isBlank()){
                productoPeso.setUrl(url);
                productoPesoServicio.saveProductoPeso(productoPeso);
            }

        }else{
            return new ResponseEntity<>("El producto "+nombre+" no existe", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Producto actualizado", (HttpStatus.OK));
    }


    //DESACTIVAR UN PRODUCTO
    @PostMapping("/api/desactivar-producto")
    public ResponseEntity<Object> desactivarProducto(Authentication authentication,
                                                  @RequestParam String nombre){
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (cliente == null){
            return new ResponseEntity<>("Debes loguearte como ADMIN para eliminar productos.", HttpStatus.FORBIDDEN);
        }
        if ( !authentication.getName().equals("admin@admin.com")){
            return new ResponseEntity<>("Solo el ADMIN puede eliminar productos.", HttpStatus.FORBIDDEN);
        }

        ProductoUni productoUni = productoUniServicio.findByNombre(nombre.toUpperCase());
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombre.toUpperCase());

        if ( productoUni != null){
            productoUni.setEstaActivo(false);
            productoUniServicio.saveProductoUni(productoUni);
        }
        if (productoPeso != null){
            productoPeso.setEstaActivo(false);
            productoPesoServicio.saveProductoPeso(productoPeso);
        }
        return new ResponseEntity<>("Producto eliminado", (HttpStatus.ACCEPTED));
    }

    //ACTIVAR UN PRODUCTO
    @PostMapping("/api/activar-producto")
    public ResponseEntity<Object> activarProducto(Authentication authentication,
                                                  @RequestParam String nombre){
        Cliente cliente = clienteServicio.findByEmail(authentication.getName());

        if (cliente == null){
            return new ResponseEntity<>("Debes loguearte como ADMIN para eliminar productos.", HttpStatus.FORBIDDEN);
        }
        if ( !authentication.getName().equals("admin@admin.com")){
            return new ResponseEntity<>("Solo el ADMIN puede eliminar productos.", HttpStatus.FORBIDDEN);
        }

        ProductoUni productoUni = productoUniServicio.findByNombre(nombre.toUpperCase());
        ProductoPeso productoPeso = productoPesoServicio.findByNombre(nombre.toUpperCase());

        if ( productoUni != null){
            if (productoUni.getEstaActivo()){
                return new ResponseEntity<>("El producto: "+nombre+" .Ya esta activo.", HttpStatus.FORBIDDEN);
            }
            productoUni.setEstaActivo(true);
            productoUniServicio.saveProductoUni(productoUni);
        }
        if (productoPeso != null){
            if (productoPeso.getEstaActivo()){
                return new ResponseEntity<>("El producto: "+nombre+" .Ya esta activo.", HttpStatus.FORBIDDEN);
            }
            productoPeso.setEstaActivo(true);
            productoPesoServicio.saveProductoPeso(productoPeso);
        }

        return new ResponseEntity<>("Producto Activado", (HttpStatus.ACCEPTED));
    }
}

