package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.PaisProducto;
import com.ricofiambre.ecomerce.modelos.TipoProducto;

public class ModificarProductoDTO {
    private String nombre;
    private String nuevoNombre;
    private String tipo;
    private String descripcion;
    private String stock;
    private String precio;
    private String origen;
    private String url;

    //GETTERS
    public String getNombre() {return nombre;}
    public String getNuevoNombre() {return nuevoNombre;}
    public String getTipo() {return tipo;}
    public String getDescripcion() {return descripcion;}
    public String getStock() {return stock;}
    public String getPrecio() {return precio;}
    public String getOrigen() {return origen;}
    public String getUrl() {return url;}
}
